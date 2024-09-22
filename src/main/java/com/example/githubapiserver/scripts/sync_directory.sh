ls
cd "$SYSTEM_GITHUB_REPOS_PATH/$1" || echo "directory not found: $1" && exit # cd /{저장 중인 위치의 디렉토리}/{요청 유저의 디렉토리}
git pull "$3" "$4" # git pull {remote_repo_name} {remote_branch}

# 동기화 작업 종료

