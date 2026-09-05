
`ProblemDetail` 
--------------------

in Spring Framework is a standard object for representing **HTTP API errors** in a consistent format. It follows **RFC 9457** (previously RFC 7807), commonly called “Problem Details for HTTP APIs.”

Instead of returning different error formats for different exceptions, an API can return a structured JSON response such as:

```json
{
  "type": "https://example.com/problems/vehicle-not-found",
  "title": "Vehicle not found",
  "status": 404,
  "detail": "No vehicle exists with VIN 1HGCM82633A123456",
  "instance": "/vehicles/1HGCM82633A123456"
}
```

The main fields are:

- `type`: URI identifying the kind of problem
- `title`: Short, general description of the problem
- `status`: HTTP status code, such as `400` or `404`
- `detail`: Specific explanation for this request
- `instance`: URI identifying the particular occurrence of the problem

Spring provides it through `org.springframework.http.ProblemDetail`.

### Returning a `ProblemDetail`

```java
@GetMapping("/vehicles/{vin}")
public ResponseEntity<ProblemDetail> getVehicle(@PathVariable String vin) {
    ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);

    problem.setTitle("Vehicle not found");
    problem.setDetail("No vehicle exists with VIN: " + vin);
    problem.setType(URI.create("https://example.com/problems/vehicle-not-found"));

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problem);
}
```

The response might be:

```json
{
  "type": "https://example.com/problems/vehicle-not-found",
  "title": "Vehicle not found",
  "status": 404,
  "detail": "No vehicle exists with VIN: 1HGCM82633A123456"
}
```

### Using it in an exception handler

A common approach is to handle an application exception with `@ExceptionHandler`:

```java
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(VehicleNotFoundException.class)
    public ProblemDetail handleVehicleNotFound(VehicleNotFoundException ex) {
        ProblemDetail problem =
                ProblemDetail.forStatusAndDetail(
                        HttpStatus.NOT_FOUND,
                        ex.getMessage()
                );

        problem.setTitle("Vehicle not found");
        problem.setType(
                URI.create("https://example.com/problems/vehicle-not-found")
        );

        return problem;
    }
}
```

Because the return type is `ProblemDetail`, Spring automatically serializes it as JSON and uses the status stored in the object.

### Adding custom fields

You can add extra application-specific information with properties:

```java
ProblemDetail problem =
        ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);

problem.setTitle("Invalid vehicle registration");
problem.setDetail("Some registration data is invalid.");

problem.setProperty("field", "VIN");
problem.setProperty("reason", "VIN must contain 17 characters");
```

Result:

```json
{
  "type": "about:blank",
  "title": "Invalid vehicle registration",
  "status": 400,
  "detail": "Some registration data is invalid.",
  "field": "VIN",
  "reason": "VIN must contain 17 characters"
}
```

### Handling validation errors

For validation failures, you can create a `ProblemDetail` and include field-level errors:

```java
@ExceptionHandler(MethodArgumentNotValidException.class)
public ProblemDetail handleValidation(
        MethodArgumentNotValidException ex) {

    ProblemDetail problem = ProblemDetail.forStatusAndDetail(
            HttpStatus.BAD_REQUEST,
            "One or more fields are invalid"
    );

    problem.setTitle("Validation failed");

    Map<String, String> errors = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .collect(Collectors.toMap(
                    FieldError::getField,
                    error -> error.getDefaultMessage(),
                    (first, second) -> first
            ));

    problem.setProperty("errors", errors);

    return problem;
}
```

Example response:

```json
{
  "type": "about:blank",
  "title": "Validation failed",
  "status": 400,
  "detail": "One or more fields are invalid",
  "errors": {
    "vin": "VIN must contain 17 characters",
    "registrationDate": "Registration date is required"
  }
}
```

The key benefit is that clients receive a **predictable error structure**, making it easier to handle errors across many endpoints.