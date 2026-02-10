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
