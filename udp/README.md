# UDP Protocol
👨🏽‍💻👁 Assignment for 'Distributed Systems' subject about Implementation of UDP Protocols

## More Information
This was a project developed to learn concepts about Protocols on Distributed Systems with Java Programming Language, however, the speaking language was Portuguese 🇧🇷.

### File Upload
For this project it was created a Server/Client UDP Protocol to transfer files to a server directory. It was used a binary protocol for the communication. The first thing the client sends to the server is a header with: FILE_LENGTH + ***";"*** + FILE_NAME. After that the cliente sends n-packages with the file content, the length used was 1024 bytes, so, a file with 3000 bytes will be sent in 3 parts (2 with 1024 bytes of length and 1 with 942 bytes). The last thing the client send is a MD5 Checksum, it is a Hash code for the file. The server will receive the header and file content and save it to the main directory, after that the server will create a MD5 Checksum for the saved file and will compare with the checksum received. If both of them are equal, the file was uploaded successfully.

 The IDE used was Visual Studio Code with an extension to compile and execute Java, you can see the tutorial over [here](https://code.visualstudio.com/docs/languages/java).

The library used (different from the usual) was:
```java
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.file.*;
```

they were used for create the MD5 Checksum for the files.