README
====================

**“Domain over Mechanism”** means your code should express **what the business wants to happen**, while keeping the technical details of **how it happens** behind an interface.

For example, a business operation might be “store this customer document.” The domain code shouldn’t need to know whether the document is saved to S3, sent over SFTP, or written to local storage.

```java
// Domain-facing interface: expresses what the application needs.
public interface DocumentStore {
    void store(Document document);
}
```

The domain model describes the business data:

```java
public record Document(
        String customerId,
        String fileName,
        byte[] content
) {}
```

An application service uses the domain-facing interface:

```java
public class RegisterDocument {
    private final DocumentStore documentStore;

    public RegisterDocument(DocumentStore documentStore) {
        this.documentStore = documentStore;
    }

    public void execute(Document document) {
        validate(document);
        documentStore.store(document);
    }

    private void validate(Document document) {
        if (document.content().length == 0) {
            throw new IllegalArgumentException("Document cannot be empty");
        }
    }
}
```

The SFTP details live in an adapter:

```java
public class SftpDocumentStore implements DocumentStore {
    private final SftpClient sftpClient;

    public SftpDocumentStore(SftpClient sftpClient) {
        this.sftpClient = sftpClient;
    }

    @Override
    public void store(Document document) {
        String path = "/" + document.customerId() + "/" + document.fileName();
        sftpClient.upload(path, document.content());
    }
}
```

A different adapter could use S3 without changing the business service:

```java
public class S3DocumentStore implements DocumentStore {
    private final S3Client s3Client;
    private final String bucket;

    public S3DocumentStore(S3Client s3Client, String bucket) {
        this.s3Client = s3Client;
        this.bucket = bucket;
    }

    @Override
    public void store(Document document) {
        String key = document.customerId() + "/" + document.fileName();
        s3Client.putObject(bucket, key, document.content());
    }
}
```

The dependency direction is the key:

```text
Business logic → DocumentStore interface ← SFTP or S3 adapter
```

The business layer depends on its own abstraction, not on an AWS or SFTP library. Spring can choose which implementation to inject:

```java
@Configuration
class DocumentConfiguration {

    @Bean
    DocumentStore documentStore(SftpClient sftpClient) {
        return new SftpDocumentStore(sftpClient);
    }
}
```

In short: **name methods in business terms** (`storeDocument`, `registerPayment`), and keep protocol calls, SDK types, credentials, retries, and file paths inside infrastructure adapters. This makes the domain easier to test and lets you change mechanisms without rewriting business rules.