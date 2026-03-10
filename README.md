# Kentsel Dönüşüm Takip Sistemi

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![JavaFX](https://img.shields.io/badge/JavaFX-blue?style=for-the-badge&logo=java&logoColor=white)
![SQLite](https://img.shields.io/badge/SQLite-07405E?style=for-the-badge&logo=sqlite&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)

Bu proje, kentsel dönüşüm süreçlerini dijitalleştirmek, bina risk analizlerini takip etmek ve mülk sahibi-müteahhit ilişkilerini yönetmek amacıyla geliştirilmiş bir masaüstü uygulamasıdır. Proje, Nesne Yönelimli Programlama (OOP) prensiplerini uygulamalı olarak sergilemektedir.

## 🚀 Özellikler

- **Bina Yönetimi:** Apartman ve müstakil bina türlerini ayrı ayrı tanımlama ve yönetme.
- **Risk Analizi Takibi:** Binaların yaş, malzeme durumu ve teknik özelliklerine göre risk durumlarını kaydetme.
- **Kişi Yönetimi:** Mülk sahipleri ve müteahhitlerin bilgilerini sistemde tutma.
- **Veritabanı Entegrasyonu:** SQLite kullanarak verilerin kalıcı olarak saklanması.
- **Modern Arayüz:** JavaFX ve modern CSS (Primer Light) teması ile kullanıcı dostu deneyim.

## 🏗️ Yazılım Mimarisi (OOP Prensipleri)

Proje, yazılım mühendisliği prensiplerine uygun olarak aşağıdaki yapıları kullanır:

- **Soyutlama (Abstraction):** `Building` ve `Person` sınıfları abstract olarak tanımlanarak temel şablonlar oluşturulmuştur.
- **Kalıtım (Inheritance):** - `ApartmentBuilding` ve `DetachedBuilding` sınıfları `Building` sınıfından türetilmiştir.
  - `PropertyOwner` ve `Contractor` sınıfları `Person` sınıfından türetilmiştir.
- **Kapsülleme (Encapsulation):** Tüm sınıf alanları `private` erişim belirleyicisi ile korunmuş, verilere kontrollü erişim `Getter/Setter` metotları ile sağlanmıştır.
- **Çok Biçimlilik (Polymorphism):** Farklı bina türlerinin ortak bir liste üzerinde yönetilmesi ve türetilmiş sınıfların özgün davranışları.

## 🛠️ Teknolojiler

- **Dil:** Java 17+
- **GUI:** JavaFX (FXML tabanlı)
- **Veritabanı:** SQLite & JDBC
- **Bağımlılık Yönetimi:** Maven
- **Tasarım:** CSS (GitHub Primer Theme esintili)

## 📁 Proje Yapısı

```text
src/main/java/com/example/nypkentseldonusumtakip/
├── App.java                 # Uygulama giriş noktası
├── Controller.java          # Arayüz mantığı ve olay yönetimi
├── db.java                  # Veritabanı bağlantı ve sorgu işlemleri
├── Person.java              # Soyut Kişi sınıfı
├── PropertyOwner.java       # Mülk Sahibi sınıfı
├── Contractor.java          # Müteahhit sınıfı
├── Building.java            # Soyut Bina sınıfı
├── ApartmentBuilding.java   # Apartman türü bina
└── DetachedBuilding.java    # Müstakil tür bina
```

## ⚙️ Kurulum ve Çalıştırma

1. Projeyi bilgisayarınıza klonlayın:
   ```bash
   git clone [repo-url]
   ```
2. Proje dizinine gidin:
   ```bash
   cd NYP-Kentsel-Donusum-Takip
   ```
3. Maven bağımlılıklarını yükleyin:
   ```bash
   mvn install
   ```
4. Uygulamayı çalıştırın:
   ```bash
   mvn javafx:run
   ```

## 📊 Veritabanı Şeması

Uygulama ilk çalıştığında otomatik olarak gerekli tabloları oluşturur. SQLite veritabanı dosyası proje kök dizininde yer alır. Temel tablolar:
- `buildings`: Bina bilgileri ve risk durumu.
- `owners`: Mülk sahibi detayları.
- `contractors`: Müteahhit portföyü.

## 📜 Lisans

Bu proje eğitim amaçlı geliştirilmiştir. MIT Lisansı altında dağıtılmaktadır.
