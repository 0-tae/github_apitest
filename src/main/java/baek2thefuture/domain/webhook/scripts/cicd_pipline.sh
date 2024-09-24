# 프로젝트 폴더 이동
cd "$SYSTEM_PROJECT_ROOT_PATH" || exit

# Git pull 통해서 프로젝트 동기화
git pull origin main

# 빌드 후 재실행
./gradlew build --no-daemon
java -jar build/libs/githubapiserver-0.0.1-SNAPSHOT.jar
