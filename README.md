Brewing Database APP
===
*Projekt stworzony w ramach przedmiotu 'Bazy danych' na IV semestrze.*

## Autorzy
+ [Przemysław Wątroba](https://github.com/Przemyslaw5)
+ [Miłosz Galas](https://github.com/miloszgalas)

## O projekcie
Jako zadanie mieliśmy do zaimplementowania aplikację bazodanową o temtyce dowolnej. Mogliśmy wybrać bazę danych z której chcemy korzystać jak i technologię w jakiej chcemy pisać naszą aplikację. 
Zdecydowaliśmy się na zrobienie aplikacji bazodanowej, która przechowuje dane odnośnie precesu warzenia piwa (przebieg zacierania, chmielenia, składniki), umożliwi przeczowywanie informacji o aktualnym inwentarzu składników, automatycznie umieści logi fermentacji w bazie, oraz wyświetlenie danych (wykres temperatur fermentacji, skład i proces tworzenia danej warki, zawartość inwentarzu). 
Można dodawać nowe warki do bazy danych, chłodnie jak i nowe składniki i ich inwentarze.

Zdecydowaliśmy się na korzystanie z bazy MognoDB jak i za technologię pisania apliakcji wybraliśmy framework Java Spring Boot. Odnośnie Frontendu zdecydowaliśmy się skorzystaś z szablonu silników Thymeleaf. 

# Dostęp do aplikacji na serwerze

[Brewing APP](http://brewingdb.theliver.pl/)

# Dostęp do aplikacji lokalnie

Należy sklonować repozytorium. Następnie po zaimportowaniu wszystkich zależności, należy zmienić opcje konfiguracji, aby uruchomić aplikację z profilem `dev`. Po uruchomieniu aplikacji, dostępne jest ona w przeglądarce pod adresem: [http://localhost:8080/](http://localhost:8080/).
Mile widziane jest również jakieś narzędzie klienckie np. [Robo3T](https://robomongo.org/) do śledzenia struktury bazy danych. Narzędzie te należy podłączyć pod defaultowy adres i port dla MongoDB: `localhost:27017`


# Struktura bazy

W bazie MongoDB zostały stworzone trzy kolekcję. We wszystkich dokumentach
pole `_class` jest polem dodanym przez bibliotekę Spring Data MongoDB

## Freezer

Kolekcja `freezer` zawiera dokumenty o następującej strukturze:

```
{
    _id: ObjectId('5ee6874103d8ec60d6d16984'),
    name: 'freezer no. 1',
    address: '884  Kerry Way',
    city: 'Krakow',
    _class: 'com.agh.database.brewingdatabaseapp.model.Freezer'
}
```

- **_id** - Identyfikator chłodni
- **name** - Nazwa chłodni
- **address** - Adres
- **city** - Miasto

## Batch

Kolekcja `batch` zawiera dokumenty o następującej strukturze:

```
{
    _id: ObjectId('5ee6874103d8ec60d6d16986'),
    name: '01-WC',
    freezer: {
        _id: ObjectId('5ee6874103d8ec60d6d16984'),
        name: 'freezer no. 1',
        address: '884  Kerry Way',
        city: 'Krakow'
    },
    style: 'West Coast Ipa',
    batchType: 'WC_IPA',
    brewedDate: '2019-10-23 13:52',
    bottledDate: '2019-11-10 05:32',
    logs: [
        {
            _id: 0,
            time: '2019-11-10 10:42',
            temp_in: 11.32,
            temp_out: 12.87,
            temp_set: 12,
            epsilon: 1
        },
        ...
    ],
    batchIngredients: [
        {
            batchID: '5ee6874103d8ec60d6d16986',
            ingredientName: 'Water',
            amount: 25,
            time: 0,
            techniqueType: 'BOIL'
        },
        ...
    ],
    _class: 'com.agh.database.brewingdatabaseapp.model.Batch'
}
```

- **_id** - Identyfikator partii piwa
- **name** - Nazwa partii piwa
- **freezer** - Dane dotyczące chłodni, w której partia jest przechowywana
- **style** - Styl piwa
- **batchType** - Typ piwa
- **brewedDate** - Data warzenia
- **bottledDate** - Data butelkowania
- **logs** - Tablica zawierająca wszystkie logi
- **batchIngredients** - Tablica zawierająca użyte składniki wraz z ich ilością

## Ingredient

Kolekcja `ingredient` zawiera dokumenty o następującej strukturze:

```
{
    _id: ObjectId('5ee6874103d8ec60d6d1698a'),
    name: 'Water',
    ingredientType: 'OTHER',
    unitIngredient: 'LITRE',
    unitsInStock: 10,
    description: 'Nice water',
    inventories: [
        {
            _id: 0,
            ingredientName: 'Water',
            timeBought: '2019-12-28 15:53',
            amount: 20,
            amountAvailable: 0,
            bestBefore: '2020-08-05 12:42',
            opened: '2020-12-29 00:00'
        },
        ...
    ],
    _class: 'com.agh.database.brewingdatabaseapp.model.Ingredient'
}
```

- **_id** - Identyfikator składnika
- **name** - Nazwa Składnika
- **ingredientType** - Typ składnika
- **unitIngredient** - Jednostka składnika
- **unitsInStock** - Ilość dostępna w magazynie
- **description** - Opis
- **inventories** - Tablica zawierająca inwentarze danego składnika

# Backend

W kodzie korzystamy z profili, ponieważ lokalnie używamy jednego profilu (dev) natomiast na serwer wysyłamy aplikację, która startuje przy pomocy innego profilu (prod). 

## [Konfiguracja bazy](https://github.com/Przemyslaw5/brewing-database-app/tree/master/src/main/resources)

Znajdują się tu taj 2 pliki konfiguracyjne w którym musimy wskazać adres i port pod którym znajduje się baza MongoDB.

Konfiguracja dla profilu z którego korzystamy lokalnie (plik: application-dev.properties):

```properties
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
spring.data.mongodb.database=BrewingDatabase
```

Oraz dla profilu, z którego korzysta serwer (plik: application.properties): 

```properties
spring.data.mongodb.host=brewing_database_mongo
spring.data.mongodb.port=27017
spring.data.mongodb.database=BrewingDatabase
```

## [Kod java](https://github.com/Przemyslaw5/brewing-database-app/tree/master/src/main/java/com/agh/database/brewingdatabaseapp)

W tym folderze znajduję się cały kod Javowy.

### model

W tym module zdefiniowany jest cały model danych oraz encje, które są mapowane do poszczególnych kolekcji z bazy MongoDB

- **Batch** - Mapuje obiekt klasy Batch do kolekcji `batch`
- **BatchIngredient** - Mapuje obiekt klasy BatchIngredeint do poszczególnych elementów tablicy w kolekcji `batch`
- **BatchType** - Enum zawierający wszystkie typy piwa
- **Freezer** - Mapuje obiekt klasy Freezer do kolekcji `freezer`
- **Ingredient** - Mapuje obiekt klasy Ingredient do kolekcji `ingredient`
- **IngredientType** - Enum zawierający wszystkie typy składników
- **Inventory** - Mapuje obiekt klasy Inventory do poszczególnych elementów tablicy w kolekcji `ingredient`
- **Log** - Mapuje obiekt klasy Log do poszczególnych elementów tablicy w kolekcji `batch`
- **Mash** - Mapuje obiekt klasy Mash do poszczególnych elementów tablicy w kolekcji `batch`
- **TechniqueType** - Enum zawierający wszystkie techniki dodawania składniów
- **UnitIngredient** - Enum zawierający wszystkie jednostki składników

### repositories

W tym module zdefiniowane są klasy implementujące wzorzec DAO (mongowy) umożliwiające dostęp do bazy

- **BatchRepository** - Umożliwia dostęp do kolekcji `batch`
- **FreezerRepository** - Umożliwia dostęp do kolekcji `freezer`
- **IngredientRepository** - Umożliwia dostęp do kolekcji `ingredient`

### services

W tym module zdefiniowane są klasy, które odpowiadają za całą logikę wykorzystywaną przez aplikacje

- **MongoService** - interfejs zawierający listę podstawowych metod, które wykonywane są na repozytoriach
- **BatchService** - klasa zawierająca logikę głównie dotyczącą kolekcji `batch`
- **FreezerService** - klasa zawierająca logikę głównie dotyczącą kolekcji `freezer`
- **IngredientService** - klasa zawierająca logikę głównie dotyczącą kolekcji `ingredient`

### controllers

W tym module zdefiniowane są klasy dpowiedzialne za przygotowywanie (jak i pobieranie) danych z bazy oraz wyświetlanie treści strony pod konkretnym adresem URL. 

- **BatchController** - klasa odpowiedzialna za prezentowanie i pobieranie danych głównie dotyczących partii piwa
- **FreezerController** - klasa odpowiedzialna za prezentowanie i pobieranie danych głównie dotyczących chłodni
- **IndexController** - klasa odpowiedzialna za przygotowanie głównego widoku na stronie głównej
- **IngredientController** - klasa odpowiedzialna za prezentowanie i pobieranie danych głównie dotyczących składników

### bootstrap

W tym module zdefiniowane są klasy, które automatycznie wypełniaja bazę danych przy uruchomieniu aplikacji

- **DevDataLoader** - klasa zawierająca kod, który wypełania bazę dnaych przy uruchomieniu aplikacji lokalnie
- **ProdDataLoader** - klasa zawierająca kod, który wypełania bazę dnaych przy uruchomieniu aplikacji na serwerze

# Frontend

Frontend został zrealizowany jako aplikacja webowa, która korzysta z silnika szablonów Thymeleaf.

## [Templatki](https://github.com/Przemyslaw5/brewing-database-app/tree/master/src/main/resources/templates)

W tym folderze znajduję się cały kod zawierający templatki plików HTML.

### index.html

W tym pliku znajduje się templatka strony głównej naszej aplikacji

### batches

W tym module znajdują się templatki głównie zajmującą się tematyką partii piwa: 

- **findBatches** - wyszukiwarka, która wyszukuje partie piwa po podaniu jej poczatkowej nazwy
- **batchesList** - pokazuje ogólne informacji partii piwa, które pasuja do podanego stringa w wyszukiwarce
- **batchDetails** - pokazuje wszystkie szczegóły dotyczące wybranej partii piwa
- **new** - formularz, ktory pozwala na dodanie nowej partii piwa

### fragments/header.html

Jest to templatka, która zawiera szablon górnego navbara, który wyświeltany jest na każdej podstronie

### freezers

W tym module znajdują się templatki głównie zajmującą się tematyką chłodni: 

- **list** - pokazuje ogólne informacji wszystkich chłodni
- **freezerDetails** - pokazuje wszystkie szczegóły dotyczące wybranej chłodni oraz przechowywanej w niej partii piwa
- **new** - formularz, ktory pozwala na dodanie nowej chłodni

### ingredients

W tym module znajdują się templatki głównie zajmującą się tematyką składników: 

- **list** - pokazuje ogólne informacji wszystkich składników
- **ingredientDetails** - pokazuje wszystkie szczegóły dotyczące wybranego składnika oraz wszystkich jego inwentarzach
- **edit** - formularz umożliwiający modyfikację opisu danego skłanika
- **new** - formularz, ktory pozwala na dodanie nowego składnika
- **newInventory** - formularz, ktory pozwala na dodanie newogo inwentarza dla danego składnika

### logs/new.html

W tym pliku znajduje się formularz, który umożliwia dodanie logu dla wybranej wcześniej partii piwa
