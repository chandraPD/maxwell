[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]

<!-- PROJECT LOGO -->
<br />
<p align="center">
  <a href="https://chandrapd.github.io/maxwell-library">
    <img src="https://chandrapd.github.io/maxwell-library/auth/assets/images/bookshelf.png" alt="Logo" width="80" height="80">
  </a>

  <h3 align="center">Maxwell Library</h3>
  <p align="center">
    A dashboard for library management
  </p>
</p>


Contribute : 
Fork, create your branch and pull request into main

Kebutuhan Software
- JAVA 11
- Spring Framework
- Spring Boot 2.41
- PostgreSQL v.12

Database sudah berjalan secara online pada Heroku

Apabila ingin menjalankan secara local :

Cara Menjalankan (Lokal)

Dengan mengubah settingan ./src/main/resources/application.properties

- Buat Database di Postgreengan nama Database maxwell
- Username : postgres
- Password : {menyesuaikan dengan password Postgre}

Buka terminal, masuk ke folder project
Jalankan aplikasi  : 
```sh
mvn spring-boot:run
```

Browse ke http://localhost:8080/{endpoint}

Endpoint dapat dilihat pada folder ./src/main/java/library/maxwell/module


<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-shield]: https://img.shields.io/github/contributors/chandraPD/maxwell.svg?style=flat-square
[contributors-url]: https://github.com/chandraPD/maxwell/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/chandraPD/maxwell.svg?style=flat-square
[forks-url]:https://github.com/chandraPD/maxwell/network/members
[stars-shield]: https://img.shields.io/github/stars/chandraPD/maxwell.svg?style=flat-square
[stars-url]: https://github.com/chandraPD/maxwell/stargazers
[issues-shield]: https://img.shields.io/github/issues/chandraPD/maxwell.svg?style=flat-square
[issues-url]:https://github.com/chandraPD/maxwell/issues
[license-shield]: https://img.shields.io/github/license/chandraPD/maxwell.svg?style=flat-square
[license-url]: https://github.com/chandraPD/maxwell/main/LICENSE.txt
