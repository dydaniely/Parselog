package com.log.parser;

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
       Assertions.assertThat(shell.evaluate(() ->"com.log.Parser --startDate=2017-01-01.15:00:00 --duration=hourly --threshold=200")).isNotNull();
//            String sampllogilePath=Paths.get("src/Test/resources/").toAbsolutePath().toString();
//        shell.run(new InputProvider() {
//            private boolean invoked = false;
//                String dlogaultFilePath="src/Test/resources/";
//
//            @Override
//            public Input readInput() {
//                if (!invoked) {
//                    invoked = true;
//                    return () -> "com.log.Parser --accesslog"+sampllogilePath + "--startDate=2017-01-01.13:00:00 --duration=hourly --threshold=100";
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
