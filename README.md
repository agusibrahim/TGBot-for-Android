Membuat Bot Telegram di Android

Pada umumnya Bot Telegram dijalankan di komputer server, dan biasanya menggunakan PHP, Python atau NodeJS. Tapi berbeda kali ini, Bot Telegram berjalan pada Android dan tentunya menggunakan java.
Bot Telegram di Android tentu di tugaskan berbeda dibandingan Bot pada umumnya. Ini berguna untuk mengirim laporan kesalahan, analytic, mengirim pesan push, atau untuk kegiatan spionase. 

Disini tidak ada setup server, hanya memanfaatkan [Telegram Bot API](https://core.telegram.org/bots/api).
 Di dokomentasinya sendiri, ada 2 cara untuk mendapatkan pesan yang dikirim ke Bot, yaitu menggunakan [Long Polling](https://en.m.wikipedia.org/wiki/Push_technology#Long_polling)
 dan [Webhook](https://en.m.wikipedia.org/wiki/Webhook)
. Untuk Webhook sendiri tidak mungkin diterapkan di Android karena kapasitas Android bukan sebagai komputer server. Kita menggunakan Long Polling, yaitu mengecek secara periodik untuk mendapat data baru.

<img src="https://github.com/agusibrahim/TGBot-for-Android/blob/master/img/Screenshot_20170704-154531.png?raw=true" width="300">

Disini memperlihatkan bagaimana cara mengirim balik pesan, mengirim event Notifikasi di Klik dan Dismiss.

### Library yang di gunakan
* Async Http Client
> Http Client yang berjalan secara async dan pengaturannya tidak ribet.
* Support Library v4
> Untuk notify compat

### Langkah Setup
* Membuat BOT untuk mendapatkan Token
* Taruh token di Constant.java
* Build

### Demo
<img src="https://github.com/agusibrahim/TGBot-for-Android/blob/master/img/demo.gif?raw=true" width="300">
[![Watch video demo](https://img.youtube.com/vi/WMtDyz_apjk/0.jpg)](https://youtu.be/WMtDyz_apjk)

### License
see LICENSE.txt

