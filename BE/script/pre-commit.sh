!/bin/sh

stagedFiles=$(git diff --staged --name-only)

echo "stage 상태의 파일에 spotless를 적용해 포매팅을 합니다."

./gradlew spotlessAppiy --daemon

for file in $stagedFiles; do
  if test -f "$file"; then
    git add "$file"
  fi
done
