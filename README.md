# LifeWise - Backend Git Flow

## • clone & push 작업흐름

### 1. Git 레포지토리 clone
~~~
git clone https://github.com/GDSC-Hongik/LifeWise-EuSha-server.git
cd LifeWise-EuSha-server
~~~

### 2. 작업할 기능의 branch 생성
~~~
git checkout -b feat/기능명
~~~
#### 예시: git checkout -b feat/login

### 3. 작업 완료 후, 현재 작업한 기능 branch push
~~~
git push origin feat/기능명
~~~
#### 예시: git push origin feat/login
## • merge 작업 흐름

### 1. 다른 팀원 PR 확인 및 merge 되는대로 원격 repo의 최신 dev 코드 -> local dev branch에 반영
~~~
git checkout dev
git pull origin dev
~~~
#### ※ 작업중인 branch에서 작업중인 내용이 있거나 commit 전 상태인 경우, dev branch로 이동이 안됨
작업코드 임시저장 후, dev branch 로 이동 
~~~
git stash 
git checkout dev
git pull origin dev
~~~
### 2. dev 코드 반영 후 작업중인 branch로 돌아가서 local dev와 코드 병합
~~~
git checkout 작업브랜치 (ex. feat/login)
git merge dev
~~~
#### ※ 임시저장한 작업코드가 있다면, 작업 내용 복원해오기
~~~
git checkout 작업브랜치 (ex. feat/login)
git merge dev
git stash pop 
~~~
