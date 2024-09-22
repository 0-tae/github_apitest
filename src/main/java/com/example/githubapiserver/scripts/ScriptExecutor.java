package com.example.githubapiserver.scripts;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Map;

@Component
@Slf4j
public class ScriptExecutor {
        public void execute(String ... args) {
            try {
                String[] command = args;
                // 쉘 스크립트 실행 명령어
                ProcessBuilder processBuilder = new ProcessBuilder(command);

                // 환경변수 설정
                Map<String, String> env = processBuilder.environment();

                processBuilder.directory(new File(System.getenv("SYSTEM_GITHUB_REPOS_PATH")));

                // 프로세스 시작
                Process process = processBuilder.start();

                // TODO: 쉘 스크립트 실행 결과 출력 읽기, 로그를 저장하기
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                BufferedWriter writer=new BufferedWriter(new FileWriter("output.txt"));

                String line;
                while ((line = reader.readLine()) != null) {
                    writer.write(line+"\n");
                }
                writer.flush();

                // 프로세스가 끝날 때까지 기다림
                int exitCode = process.waitFor();
                System.out.println("\nExited with code : " + exitCode);

            } catch (Exception e) {
                e.printStackTrace();
            }
    }
}
