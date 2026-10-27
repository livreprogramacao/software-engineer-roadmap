README
====================

I’m going to explain the acronym FIRST, which stands for Fast, Independent, Repeatable, Self-validating, and Timely, particularly in the context of unit testing. I’ll provide examples using JUnit for service testing, avoiding the Spring context for these unit tests. For integration tests, I'll reference SpringBootTest. Timely testing is crucial here. I might also mention using isolated test containers for the database to ensure consistent testing. Let's get into the code!
**FIRST** is a set of qualities that make tests trustworthy and easy to run:

- **Fast:** Tests finish quickly, so developers run them often.
- **Independent:** One test doesn’t rely on another test’s data or execution order.
- **Repeatable:** The same test gives the same result each time, regardless of machine or environment.
- **Self-validating:** The test reports pass or fail automatically through assertions—not by someone inspecting output.
- **Timely:** Tests are written alongside the code, ideally before or as the feature is implemented, while expected behavior is clear.

With Spring, a useful approach is to test business logic as a plain unit test where possible, without starting the whole application. For example:

```java
class UploadFileTest {
    private final FileStorage storage = Mockito.mock(FileStorage.class);
    private final UploadFile uploadFile = new UploadFile(storage);

    @Test
    void storesFileWithProvidedNameAndContent() {
        byte[] content = "hello".getBytes();

        uploadFile.execute("report.txt", content);

        Mockito.verify(storage).store("report.txt", content);
    }
}
```

This test is fast because it doesn’t start Spring or contact SFTP; independent because it creates its own mock and service; repeatable because it uses fixed input; and self-validating because Mockito verifies the expected call.

Use Spring integration tests when you need to verify wiring, configuration, or actual interaction with infrastructure. Those tests are usually slower, so keep them focused and make sure they use controlled test data rather than depending on leftover state.