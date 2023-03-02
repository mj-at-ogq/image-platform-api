## 이미지 플랫폼 API 서버

### 가. 검색 요구사항 정리
- 일반적으로 업로드된 이미지는 모든 마켓에서 검색을 허용한다.
- publicity right(공개권)이 설정된 이미지는 해당 공개권이 설정된 마켓에서만 검색을 허용한다.
- 공개권이 설정되지 않은 이미지는 모든 마켓에서 검색을 허용한다.

### 나. 검색 기능 설계
- Flow Chart
<img width="296" alt="image" src="https://user-images.githubusercontent.com/16694346/222410369-65f48c62-546a-4017-9337-9e23c693e1d2.png">

- UML
<img width="457" alt="image" src="https://user-images.githubusercontent.com/16694346/222410455-ee7e132a-d01d-44b8-82ed-862747b6215c.png">

- ERD
<img width="507" alt="image" src="https://user-images.githubusercontent.com/16694346/222410508-4448e08d-1ff0-40dc-8201-68fec35097ec.png">

### 다. 참고
- Publicity Right(공개권)이란 개인의 콘텐츠(이미지)를 상업적인 목적으로 이용하는 것을 통제할 수 있는 권리이다. 해당 권리를 주장함으로써 타인에 의한 자신의 콘텐츠의 의도치 않은 사용을 방지할 수 있다. 따라서 공개권이 설정된 이미지는 해당 공개권이 허용된 마켓에서만 사용과 배포가 허용된다. - 위키피디아 - 
