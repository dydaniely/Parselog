package com.ef.parser;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.shell.Input;
import org.springframework.shell.InputProvider;
import org.springframework.shell.Shell;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.nio.file.Paths;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestRunner.class})
@Import(TestRunner.class)
public class ParserTest {

    @Autowired
    private Shell shell;


    @Test
    public void whenTheCommandWorksReturnTrue() throws IOException {
/* given */
       Assertions.assertThat(shell.evaluate(() ->"com.ef.Parser --startDate=2017-01-01.15:00:00 --duration=hourly --threshold=200")).isNotNull();
//            String sampleFilePath=Paths.get("src/Test/resources/").toAbsolutePath().toString();
//        shell.run(new InputProvider() {
//            private boolean invoked = false;
//                String defaultFilePath="src/Test/resources/";
//
//            @Override
//            public Input readInput() {
//                if (!invoked) {
//                    invoked = true;
//                    return () -> "com.ef.Parser --accesslog"+sampleFilePath + "--startDate=2017-01-01.13:00:00 --duration=hourly --threshold=100";
//                } else {
//                    return () -> "exit";
//                }
//
//
//            }
//        });
    }

}


//}
