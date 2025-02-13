# HttpLoggingStarter
1. Согласно моему пониманию ТЗ сделал аннотацию которая должна перехватывать **все HTTP запросы**

```shell
curl -X GET http://127.0.0.1:8080/test

```

Проблему которую надо решить: @LogHTTP не работает над Main классом приложения
@SpringBootApplication
public class Main{

}
