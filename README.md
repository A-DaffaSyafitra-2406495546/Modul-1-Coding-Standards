# Reflection 1
## Clean Code Principles Applied
1. Separation of concerns
2. Meaningful naming
3. Small and focused methods
4. Consistent structure

## Secure Coding Practices Applied
1. Input Handling
2. Avoiding NullPointerException
3. Controlled data modification

## Issues Found and Improvements
### 1. Using productName as an identifier
### Improvements: menggunakan productID yg secara otomatis terbuat

# Reflection 2

## 1. After writing the unit test, how do you feel? How many unit tests should be made in a class? How to make sure that our unit tests are enough to verify our program? It would be good if you learned about code coverage. Code coverage is a metric that can help you understand how much of your source is tested. If you have 100% code coverage, does that mean your code has no bugs or errors?
Setelah membuat unit test saya merasa lebih yakin dengan kodenya, karena saya jadi tahu bagaimana setiap method bekerja sehingga saya bisa menemukan bug lebih mudah, jumlah unit test dalam satu class tidak pasti yang penting minimal ada test untuk normal,gagal dan base. jika code coverage mencapai 100% bukan berarti kodenya bebas bug, tetapi coverage memastikan kalau baris kode itu dijalnkan di unit test tersebut.
## 2. Suppose that after writing the CreateProductFunctionalTest.java along with the corresponding test case, you were asked to create another functional test suite that verifies the number of items in the product list. You decided to create a new Java class similar to the prior functional test suites with the same setup procedures and instance variables. What do you think about the cleanliness of the code of the new functional test suite? Will the new code reduce the code quality? Identify the potential clean code issues, explain the reasons, and suggest possible improvements to make the code cleaner!
Jika saya membuat functional test baru dengan menyalin struktur CreateProductFunctionalTest, kebersihan kode akan menurun. Setup yang sama akan muncul di banyak class dan menyebabkan duplikasi kode. Hal ini membuat kode lebih sulit dipahami.  Kualitas kode juga bisa berkurang karena perubahan kecil. Kondisi ini meningkatkan risiko kesalahan dan inkonsistensi antar test.  Masalah clean code yang berpotensi muncul adalah duplikasi kode setup dan variabel, test menjadi lebih panjang, serta adanya ketergantungan antar test. Semua ini membuat managing test menjadi tidak efisien.  Perbaikan yang dapat dilakukan adalah memindahkan setup umum ke satu base class khusus untuk functional test, menggunakan helper method untuk aksi yang sering digunakan, memastikan setiap test hanya menguji satu perilaku, dan memberi nama test yang jelas sesuai tujuan pengujian. Dengan pendekatan ini, functional test tetap ringkas, mudah dibaca, dan mudah dimanage meskipun jumlah test bertambah.

# Reflection - SOLID Principles

## 1) Prinsip SOLID yang Diterapkan

Single Responsibility Principle (SRP)
CarController dipisahkan dari ProductController sehingga masing-masing class hanya memiliki satu tanggung jawab. CarController hanya menangani request HTTP terkait mobil, CarServiceImpl hanya menangani business logic, dan CarRepository hanya menangani akses data.

Open/Closed Principle (OCP)
Dibuat interface Vehicle dan CarService sehingga jika ingin menambahkan tipe kendaraan baru (misalnya Truck atau Motorcycle), cukup membuat class baru yang mengimplementasi interface tersebut tanpa mengubah kode yang sudah ada.

Liskov Substitution Principle (LSP)
Car mengimplementasi interface Vehicle, sehingga objek Car dapat digunakan di mana pun Vehicle diharapkan tanpa mengubah kebenaran program.

Interface Segregation Principle (ISP)
CarService dan ProductService dibuat sebagai interface yang terpisah dan spesifik. CarController hanya perlu mengetahui method yang relevan dari CarService, tidak dipaksa bergantung pada method ProductService yang tidak dibutuhkan.

Dependency Inversion Principle (DIP)
CarController sebelumnya bergantung pada CarServiceImpl (concrete class). Setelah diterapkan DIP, CarController bergantung pada CarService (interface/abstraksi), sehingga high-level module tidak bergantung pada low-level module.

## 2) Keuntungan Menerapkan SOLID

SRP: Dengan memisahkan CarController dari ProductController, jika ada perubahan pada fitur Car tidak akan berdampak pada fitur Product. Kode lebih mudah dibaca dan di-maintain.

OCP: Jika ingin menambahkan kendaraan baru seperti Truck, cukup membuat TruckService yang mengimplementasi interface yang sudah ada tanpa menyentuh CarService atau ProductService.

LSP: Karena Car mengimplementasi Vehicle dengan benar, kode yang menerima Vehicle akan tetap berjalan dengan benar saat menerima objek Car.

ISP: CarController tidak perlu mengimplementasi method deleteProductById dari ProductService yang tidak relevan untuknya karena interface sudah dipisah.

DIP: Dengan bergantung pada CarService interface, kita bisa mengganti implementasi CarServiceImpl dengan implementasi lain (misalnya untuk testing) tanpa mengubah CarController.



## 3) Kerugian Tidak Menerapkan SOLID

SRP: Pada before-solid, CarController meng-extend ProductController dan berada dalam satu file yang sama. Jika ada bug pada fitur Product, bisa berdampak pada fitur Car karena saling terkait.

OCP: Setiap kali ingin menambahkan jenis kendaraan baru, kita harus mengubah class yang sudah ada sehingga berisiko merusak fungsionalitas yang sudah berjalan.

LSP: Jika subclass tidak mengimplementasi interface dengan benar, program bisa menghasilkan perilaku yang tidak terduga saat subclass digunakan menggantikan superclass-nya.

ISP: Jika CarService dan ProductService digabung menjadi satu interface besar, CarController terpaksa bergantung pada method-method Product yang tidak relevan, membuat kode menjadi lebih sulit dipahami dan di-maintain.

DIP: Pada before-solid, CarController bergantung langsung pada CarServiceImpl. Jika ingin mengganti implementasi service (misalnya saat unit testing dengan mock), kita harus mengubah kode CarController juga.
