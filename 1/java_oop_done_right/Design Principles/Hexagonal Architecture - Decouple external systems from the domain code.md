README
====================

**Hexagonal Architecture** (also called **Ports and Adapters**) keeps business rules independent of frameworks, databases, APIs, and other external systems.

Think of the application’s **domain and use cases as the center**. They define what they need through **ports**—interfaces. **Adapters** implement those interfaces to connect the application to external tools such as Spring MVC, a database, S3, or an SFTP server.

```text
HTTP / Spring controller ──> Use case ──> Port <── S3 or SFTP adapter
Database adapter ────────────────┘
```

The key dependency rule is: **outer layers depend on the core; the core does not depend on outer layers.** For example, the domain should not import Spring annotations or AWS SDK classes.

### Small Spring example

The port describes the capability the use case needs:

```java
public interface FileStorage {
    void store(String fileName, byte[] content);
}
```

A use case depends on that interface—not on S3 or SFTP:

```java
public class UploadFile {
    private final FileStorage storage;

    public UploadFile(FileStorage storage) {
        this.storage = storage;
    }

    public void execute(String fileName, byte[] content) {
        // Business rules can go here.
        storage.store(fileName, content);
    }
}
```

An adapter implements the port using a specific external system:

```java
import org.springframework.stereotype.Component;

@Component
public class SftpFileStorage implements FileStorage {
    @Override
    public void store(String fileName, byte[] content) {
        // Connect to SFTP and upload the file.
    }
}
```

Spring MVC is another adapter: it translates an HTTP request into a call to the use case.

```java
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/files")
public class FileController {
    private final UploadFile uploadFile;

    public FileController(UploadFile uploadFile) {
        this.uploadFile = uploadFile;
    }

    @PostMapping
    public void upload(@RequestParam MultipartFile file) throws Exception {
        uploadFile.execute(file.getOriginalFilename(), file.getBytes());
    }
}
```

In a fuller application, keep Spring annotations and HTTP-specific types such as `MultipartFile` in the adapter layer; pass plain Java data into the use case. Also wire the use case as a Spring bean, for example with `@Configuration` and `@Bean`.

**Why use it?** You can replace SFTP with S3, change the database, or test the business logic without starting Spring or connecting to AWS. The use case and its port stay stable; only the adapter changes.