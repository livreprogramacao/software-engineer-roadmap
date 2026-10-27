README
====================

**Collaboration** is the design question: **“Which object should do this job, and which other objects should it ask for help?”**

In an object-oriented Spring application, assign each responsibility to the object that has the right information and authority to handle it. Keep objects focused, and let them collaborate through clear methods or interfaces instead of making one class do everything.

For example, uploading a file might involve three responsibilities:

- The **controller** handles HTTP details and passes the request to the application.
- The **use case/service** coordinates the upload workflow and applies business rules.
- The **storage adapter** handles the SFTP or S3-specific transfer.

```java
@RestController
@RequestMapping("/files")
class FileController {
    private final UploadFile uploadFile;

    FileController(UploadFile uploadFile) {
        this.uploadFile = uploadFile;
    }

    @PostMapping
    void upload(@RequestParam String name, @RequestBody byte[] content) {
        uploadFile.execute(name, content);
    }
}
```

```java
@Service
class UploadFile {
    private final FileStorage storage;

    UploadFile(FileStorage storage) {
        this.storage = storage;
    }

    void execute(String name, byte[] content) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("File name is required");
        }

        storage.store(name, content);
    }
}
```

```java
interface FileStorage {
    void store(String name, byte[] content);
}
```

```java
@Component
class SftpFileStorage implements FileStorage {
    @Override
    public void store(String name, byte[] content) {
        // SFTP-specific upload code belongs here.
    }
}
```

Here, the controller should **not** open an SFTP connection: that’s not its job. The storage adapter should **not** decide whether a file name is allowed: that’s a business rule for the use case or domain. The service collaborates with storage through `FileStorage`, so it doesn’t need to know whether the implementation uses SFTP, S3, or something else.

A useful rule of thumb: put a responsibility where the relevant knowledge lives. If a class is growing to handle HTTP, business decisions, and file-transfer details, split those responsibilities and have the objects collaborate.