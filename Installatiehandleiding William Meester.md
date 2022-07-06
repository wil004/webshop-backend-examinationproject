Installatiehandleiding William Meester Webshop applicatie INLEIDING

2.                     Introductie

3.                      Installeren van de benodigde software.

3 ™ 4 Het configureren en starten van de applicatie.

5.                     Het gebruik van postman en het importeren van een 

    json collection.

6 ™ 15 Alle applicatie endpoints. \* Api/v1/user 6\
\* Api/v1/auth 7\
\* Api/v1/product 7™8\
\* Api/v1/attachment 8 \* Api/v1/shoppingcart 9\
\* Api/v1/customer 9™10 \* Api/v1/order 11™12 \* Api/v1/returns 12™13 \*
Api/v1/pdf 14 \* Api/v1/employee 14™15

15 Slot

Introductie Het correct installeren van een applicatie en de
bijbehorende software is een must. Om ervoor te zorgen dat de applicatie
goed kan worden gedraaid, zal er in deze installatiehandleiding goed
omschreven worden hoe je de app werkend krijgt. Als alle stappen goed
doorlopen zijn dan kan je zonder problemen gebruiken maken van de
webshop app.

Installeren van de benodigde software Downloaden van de intelliJ ide
(integrated development environment) Allereerst zal er een ide moeten
worden gedownload! Download intelliJ via deze link IntelliJ IDEA: The
Capable & Ergonomic Java IDE by JetBrains Dit is belangrijk omdat dit de
ide is waarop je je programma draait en waar al je code in staat.
Windows 10: How to Install IntelliJ IDEA on Windows 10 - YouTube Mac:
How to Install IntelliJ IDEA on Mac - YouTube

Downloaden java development kit versie 16 Allereerst zal er een jdk
versie 16 (java development kit) moeten worden gedownload. Doe dit via
deze link Java Archive Downloads - Java SE 16 (oracle.com) Mocht je hier
niet uit komen dan kun je 1 van de de volgende video's bekijken: Windows
10: How to Install Java 16 on Windows 10 - YouTube Mac: How to Install
Java JDK on Mac OS X \[2022\] - YouTube

Downloaden van postgreSQL en pgAdmin4 Je wilt ook een database hebben
waarin je data van het project wordt opgeslagen. Deze download je op
https://www.postgresql.org/download Deze volgende video's leggen stap
voor stap uit hoe dit moet. Windows 10: How to Install and Setup
PostgreSQL on Windows 10 - YouTube. Mac: How to install PostgreSQL on
macOS \| Postgres App & PgAdmin - YouTube Vervolgens kan je gebruik
maken van postgreSQL met de pgAdmin4 applicatie.

Downloaden van postman Om te kijken wat de backend applicatie kan je
postman gebruiken. Postman download je hier: Download Postman \| Get
Started for Free De volgende video's leggen uit hoe je postman download.
Windows 10: How To Download and Install Postman In Windows Postman
Tutorial - YouTube Mac: How to Install Postman for Mac OSX - YouTube

Het configureren van de applicatie. Clone het project uit github.
https://github.com/wil004/webshop-backend-examinationproject.git

Kies een map naar keuze waar je je project in wilt clonen en voer
hiervoor de bovenstaande command in.

Configureer de database Nu je het project gecloned hebt is het
belangrijk de database goed in te stellen! Registreer in pgAdmin4 een
nieuwe server! (mocht je nog geen server hebben dan maak je die
eenvoudig door op create en dan op servergroup... te klikken en een naam
te kiezen. Als je deze server hebt aangemaakt, klik hier dan met je
rechtermuisknop op en klik op register en vervolgens op server... zoals
hieronder staat afgebeeld.

Vervolgens kies je een naam uit in general en ga je naar connection. In
connection zorg je ervoor dat de Host name localhost staat op de port:
5432 stel de username in op: postgres en het password op: springboot Let
erop dat je alles correct invult dit is namelijk belangrijk om je
applicatie met de database te verbinden!

Maak vervolgens een database genaamd webshop aan. Dit doe je door met je
rechtmuisknop op Databases te klikken, vervolgens een naam in te vullen
onder het kopje database: en dan op save te klikken.

Controleer ook of de onderste user in Login/Group Roles ook
daadwerkelijk postgres heet.

Als dit allemaal klopt kan je de applicatie gebruiken dit komt omdat de
application.properties daarop is ingesteld! Hoe dit eruit ziet in de
applicatie:

application.properties

Start het project door in WebshopApplication op de play knop te klikken,
als alle stappen goed gevolgd zijn dan draait de applicatie nu.

Het gebruik van postman en het importeren van een json collection.
Uitleg over postman Wanneer je de applicatie hebt opgestart wil je hier
natuurlijk gebruik van maken. Echter is het niet handig om dit meteen in
de front-end op te nemen zonder dat je ziet wat bepaalde endpoints van
je applicatie doen. Sterker nog misschien maak je zelf helemaal geen
front-end voor de applicatie en besteed je deze taak uit aan een ander.
Een manier om te kijken of je applicatie naar behoren werkt is dus het
gebruiken van postman.

Voor deze applicatie wordt er gebruik gemaakt van de endpoint
http://localhost:8080/ Alles wat erachter staat is een endpoint van de
applicatie. In de volgende paragraaf zullen deze endpoints in de vorm
van API/V1/endpoint worden uitgelegd. Hier is het belangrijk om te weten
dat je methodes kan getten posten putten, deleten en nog veel meer. Voor
mijn applicatie zijn alleen de get, post, put en delete requests van
belang.

Het importeren van een json collection. Omdat mijn applicatie vrij groot
is kan het behoorlijk lang duren om alle endpoints stuk voor stuk te
kopiëren en te plakken en te verwerken. Om deze reden heb ik een
vooropgezet bestand gemaakt met alle endpoints! Dit bestand genaamd een
collection.json kan worden geïmporteerd in postman. Wanneer dit wordt
gedaan worden alle requests en endpoints die ik heb opgezet beschikbaar
om direct te kunnen gebruiken.

Klik op import upload je collection.json bestand. Vervolgens krijg je
een lijst met bestanden binnen. LET OP!: Voor het gebruik van deze
applicatie dient de lijst met requests in de correcte volgorde te worden
verzonden. Begin bij user/create-admin Ook wordt er geen al vooraf
ingevulde sql meegestuurd omdat dit de functionaliteit van de applicatie
lastiger te controleren maakt.

Alle applicatie endpoints De webshop applicatie heeft in totaal 47
endpoints. Omdat ik me besef dat dit behoorlijk veel endpoints zijn en
om het controleren van de applicatie efficiënt te houden, heb ik ervoor
gekozen niet alle endpoints in het collection.json file te uploaden.
Alle endpoints werken! Ik zal aangeven met een \* welke endpoints er in
het json bestand staan.

API/V1/user

-   @POST API/v1/user/create-admin Functie: Maakt een admin account aan!
    Er kan maar 1 admin account zijn net zoals er maar 1 webshop kan
    zijn! Autorisatie: PermitAll(), de aanmaker van de webshop maakt een
    account en er kan er maar eentje zijn dus permitAll. body: {
    "emailAddress": "emailaddress", "username": "user1", "password":
    "password", "bankAccount": "1234" }

-   @POST API/v1/user/create-employee Functie: Maakt een employee
    account aan! Autorisatie: hasAuthority("ADMIN") Alleen een admin kan
    dit account aanmaken. body: { "emailAddress": "emailaddress",
    "username": "employee", "password": "password", "firstName":
    "employee", "lastName": "Meester" }

API/V1/auth

-   @POST API/v1/auth Functie: Logged de gebruiker in als de
    accountgegevens correct zijn en returned een jwt token. Autorisatie:
    permitAll(), iedereen moet kunnen inloggen. body: { "username":
    "user1", "password": "password" }

API/v1/product

-   @GET API/v1/product Functie: Laat een lijst van alle producten zien.
    Autorisatie: permitAll(), iedereen moet producten kunnen ophalen.

@GET API/v1/product/name/{String productName} Functie: Haalt een product
op aan de hand van de naam. Autorisatie: permitAll(), Iedereen moet een
product kunnen opzoeken.

-   @GET API/v1/product/category/{String category} Functie: Haalt een
    product op aan de hand van de product categorie. Autorisatie:
    permitAll(), iedereen moet producten op categorie kunnen filteren.

@GET API/v1/product/price/{double minimumPrice}/{double maximumPrice}
Functie: Haalt een product op binnen een bepaalde prijsklasse.
Autorisatie: permitAll(), iedereen moet kunnen filteren op prijs.

-   @PUT API/v1/product/change/{Long id}/{String type} Functie:
    Verandert een product type met de gegeven naam. Autorisatie:
    hasAuthority("ADMIN") alleen de admin kan het type veranderen body:
    { "productName": "changeToThis" }

-   @POST API/v1/product Functie: Maakt een nieuw product aan.
    Autorisatie: hasAuthority("ADMIN") alleen een admin kan nieuwe
    producten aanmaken. body: { "productName": "kaas", "category":
    "food", "price": 20 }

@DELETE API/v1/product/delete/{Long id} Functie: Verwijdert een product!
Dit kan alleen als een product nog niet gekoppeld is aan een andere
klasse (bijvoorbeeld bij een verkeerde invoer van de post
api/v1/product) Dit heeft te maken met dat bestelde producten niet
zomaar verwijdert mogen worden! Autorisatie: hasAuthority("ADMIN")
alleen een admin kan producten verwijderen.

API/v1/attachment

-   @POST API/v1/attachment/upload Functie: Upload een file dit endpoint
    wordt gebruikt om afbeeldingen up te loaden. Autorisatie:
    hasAuthority("ADMIN") alleen een admin kan files uploaden.
    form-data:

-   @PUT API/v1/attachment/product={Long productId}/file={String fileId}
    Functie: Koppelt een file/afbeelding aan een product. Autorisatie:
    hasAuthority("ADMIN") alleen een admin kan een file aan een product
    koppelen.

@GET API/v1/attachment/{String fileId} Functie: Haalt een file op.
Autorisatie: permitAll() iedereen moet files/afbeeldingen op kunnen
halen.

API/v1/shoppingcart

-   @PUT API/v1/shoppingcart/id={Long customerId}/productid={Long
    productId} Functie: Koppelt een product aan een shoppingcart! Een
    shoppingcart wordt automatisch aangemaakt als er een customer
    account wordt aangemaakt. Autorisatie: hasAnyAuthority("ADMIN",
    "CUSTOMER") alleen customers met een account of de admin kan
    producten in een shoppingcart toevoegen. Body: { "amountOfProducts":
    100 }

@DELETE API/v1/shoppingcart/{id} Functie: Verwijdert een shoppingcart
(endpoint is niet echt nodig) Autorisatie: hasAuthority("ADMIN") alleen
een admin kan een shoppingcart verwijderen.

API/v1/customer

-   @GET API/v1/customer Functie: Haalt alle customers op Autorisatie:
    hasAuthority("ADMIN") alleen de admin kan dit!

@GET API/v1/customer/id={id} Functie: Haalt een customer op bij id! Let
op als de gebruiker een customer is kan hij alleen zijn eigen gegevens
ophalen! Autorisatie: hasAnyAuthority("ADMIN", "EMPLOYEE", "CUSTOMER")

-   @GET API/v1/customer/orderhistory/customerid={Long id} Functie: Dit
    endpoint haalt een customer zijn bestelgegevens op! Als de gebruiker
    een customer is kan hij alleen zijn eigen bestelgegevens ophalen!
    Autorisatie: hasAnyAuthority("ADMIN", "EMPLOYEE", "CUSTOMER")

-   @POST API/v1/customer Functie: Deze endpoint creëert een customer,
    de body kan op 2 manieren ingevuld worden! Met username en password
    of zonder! Met username en password wordt er een customer account
    aangemaakt, zonder username en password is de customer een gast! Een
    customer zonder account kan dus gewoon shoppen. Als de klant
    producten besteld zonder account zal deze endpoint tegelijk moeten
    worden aangeroepen met de order endpoint. Zo voorkom je rondzwevende
    lege customers in de database. Autorisatie: permitAll() iedereen
    moet een account kunnen maken of zijn bestelling verwerken met zijn
    klantgegevens. Body:

customer zonder account (endpoint moet tegelijk worden meegegeven met de
order endpoint vanuit de front-end) { "emailAddress":
"emailAddress\@email.nl", "firstName": "William", "lastName": "Meester",
"streetName": "straat", "houseNumber": "777", "city": "Winschoten",
"zipcode": "7777VW" }

customer met account { "emailAddress": "emailaddress\@email.nl",
"username": "user1", "password": "password", "firstName": "William",
"lastName": "Meester", "streetName": "straat", "houseNumber": "777",
"additionalToHouseNumber": "A", "city": "Winschoten", "zipcode":
"7777VW" }

API/v1/order @GET API/v1/order Functie: Haalt alle orders op.
Autorisatie: hasAuthority("ADMIN") alleen de admin kan alle orders
ophalen

@GET API/v1/order/id={Long id} Functie: Haalt een order op aan de hand
van zijn id. Autorisatie: hasAuthority("ADMIN") alleen de admin kan dit
vanaf het order endpoint, de employee kan alleen zijn eigen orderlijsten
ophalen, zie meer info bij API/v1/employee

-   @GET API/v1/order/processed-status={boolean processed} Functie:
    Haalt alle verwerkte of niet verwerkte bestellingen op! Autorisatie:
    hasAuthority("ADMIN") alleen de admin kan dit!

-   @GET API/v1/order/{String firstName}/{String lastName}/{String
    zipcode}/{int houseNumber} Functie: Haalt een order op aan de hand
    van de klantgegevens. Autorisatie: hasAuthority("ADMIN") Alleen een
    admin kan elke klant opzoeken!

@GET API/v1/order/{String firstName}/{String lastName}/{String
zipcode}/{int houseNumber}/{String additionalHouseNumber} Functie:
Zelfde als het endpoint erboven maar dan wanneer de klant een extra
huisnummer heeft als in bijvoorbeeld een letter. Autorisatie:
hasAuthority("ADMIN") Alleen een admin kan elke klant opzoeken!

@PUT API/v1/order/paid-order/id={orderId} Functie: Verandert de status
van of een bestelling betaald is naar true! Betalingen zullen buiten
deze applicatie om gecontroleerd moeten worden op het bankaccount van de
webshop! Autorisatie: hasAuthority("ADMIN") Alleen een admin kan dit
vanuit het order endpoint.

@PUT API/v1/order/change-order-processed-status={boolean
processed}/id={Long orderId} Functie: Wijzigt de verwerkte status van
een bestelling! Stel een admin heeft zelf een order verwerkt dan zet hij
deze op true. Stel er is iets fouts gegaan dan zet hij deze terug op
false. Een order kan alleen verwerkt worden als hij betaald is! Een
order verwerkt door de admin zal uit een employee zijn orderlijst worden
gehaald als een employee dezelfde order in zijn lijst heeft!
Autorisatie: hasAuthority("ADMIN") Alleen een admin kan de status van
elke bestelling wijzigen.

-   @POST API/v1/order/customer={Long customerId} Functie: Plaatst een
    bestelling vanuit een customer account! De shoppingcart van de klant
    zal geleegd worden in de order entiteit en de order zal nu deze
    producten bevatten. Autorisatie: hasAnyAuthority("ADMIN",
    "CUSTOMER") een admin en een customer kunnen dit doen! Body: Een
    lege body volstaat {}

-   @POST API/v1/order/guest={Long customerId} Functie: Plaatst een
    bestelling vanuit een customer die geen account heeft! Dit endpoint
    zal tegelijk (vlak na) het aanmaken van een guest customer moeten
    worden aangeroepen. De shoppingcart wordt meegestuurd vanuit de
    front-end Autorisatie: permitAll() iedereen moet een bestelling
    kunnen plaatsen! Body: { "productList": \[ { "id": 2,
    "amountOfProducts": 15 }, { "id": 3, "amountOfProducts": 5 } \] }

API/v1/returns

-   @GET API/v1/returns Functie: Haalt alle retouren op. Autorisatie:
    hasAuthority("ADMIN") alleen de admin kan alle retouren ophalen.

@GET API/v1/returns/processed-status={boolean processed} Functie: Haalt
alle retouren op met een false of true processed status. Autorisatie:
hasAuthority("ADMIN")

-   @GET API/v1/returns/{String firstName}/{String lastName}/{String
    zipcode}/{int houseNumber} Functie: Haalt retouren op aan de hand
    van klantgegevens. Autorisatie: hasAuthority("ADMIN")

@GET API/v1/{String firstName}/{String lastName}/{String zipcode}/{int
houseNumber}/{String additionalHouseNumber} Functie: Haalt retouren op
aan de hand van klantgegevens met een extra huisnummer (bijv. een
letter). Autorisatie: hasAuthority("ADMIN")

-   @PUT API/v1/returns/change-processed-status={boolean
    processed}/id={Long returnId} Functie: Verandert de verwerkt status
    van een returns naar true of false. Wanneer een returns naar true
    wordt verandert wordt deze automatisch verwijdert uit een employee
    zijn retouren lijst! Hij hoeft deze dan immers niet meer te
    verwerken. Autorisatie: hasAuthority("ADMIN")

-   @PUT API/v1/returns/id={Long returnsId}/productId={Long productId}
    Functie: Voegt een product toe aan een retouren lijst! Dit kan
    alleen als de orderlijst waar deze retouren toe behoren niet ouder
    is dan 30 dagen. Autorisatie: permitAll() iedereen moet retouren
    kunnen toevoegen wel moet er in de front-end gevraagd worden om een
    orderId of terwijl een bestelnummer! Body: {
    "amountOfReturningProducts": 7 }

-   @POST API/v1/returns/{Long orderId} Functie: Maakt een returns
    object aan waarin producten kunnen worden gestopt! Dit kan tot
    maximaal 30 dagen nadat de bestelling geplaatst is! Autorisatie:
    Iedereen moet retouren kunnen aanmaken. Body: {
    "bankAccountForReturn": "bank1241234" }

API/v1/pdf \* @GET API/v1/pdf/generate-order/id={Long orderId} Functie:
Maakt een pdf op van het json object van de order, hierin staan
betaalinstructies die de klant moet opvolgen om te betalen. Of de
betaling is binnengekomen wordt gecontroleerd op het bankaccount van de
webshop (buiten deze applicatie om) Autorisatie: permitAll() iedereen
moet een factuur van zijn bestelling kunnen ophalen.

-   @GET API/v1/pdf/generate-return/id={Long returnsId} Functie: Werkt
    hetzelfde als het endpoint voor de order pdf maar is bedoelt voor de
    retouren. Hierin staan ook instructies over hoe je de retouren moet
    terugsturen. Autorisatie: permitAll() iedereen moet een
    retourbevestiging kunnen ophalen.

API/v1/employee Handig om te weten: Elke employee heeft een lijst met
orders en retouren die hij nog moet verwerken en een lijst met verwerkte
orders en verwerkte retouren! \* @GET API/v1/employee Functie: Haalt
alle werknemers op. Autorisatie: hasAuthority("ADMIN")

-   @GET API/v1/employee/id={Long employeeId} Functie: Haalt de gegevens
    op van een employee vanuit zijn id! Als de gebruiker een employee is
    kan hij alleen zijn eigen gegevens ophalen. Autorisatie:
    hasAnyAuthority("ADMIN", "EMPLOYEE")

-   @PUT API/v1/employee/confirm-payment/employee-id={Long
    employeeId}/order-id={Long orderId}/ispaid={boolean isPaid} Functie:
    Zet een bestelling zijn betaalstatus, die zich in de orderlijst van
    de employee bevindt op true of false. Als deze op true wordt gezet
    kan de employee verder gaan met het verwerken van de bestelling. Als
    de gebruiker een employee is dan kan hij alleen orders die in zijn
    eigen orderlijst zitten wijzigen. Autorisatie:
    hasAnyAuthority("ADMIN", "EMPLOYEE")

-   @PUT API/v1/employee/process-order/employee-id={Long
    employeeId}/order-id={Long orderId} Functie: Als een bestelling
    betaald is kan deze verwerkt worden! Een employee kan alleen
    bestellingen die in zijn eigen lijst zitten verwerken! Na verwerking
    wordt deze bestelling verwijdert uit de orderList en toegevoegd aan
    de employee zijn finishedOrderList. Autorisatie:
    hasAnyAuthority("ADMIN", "EMPLOYEE")

-   @PUT API/v1/employee/process-order/employee-id={Long
    employeeId}/order-id={Long orderId} Functie: Doet hetzelfde als de
    proces order endpoint hierboven maar om dit endpoint aan te roepen
    hoeft de betaalstatus niet op true te staan! Returns heeft namelijk
    geen betaalstatus omdat het bedrijf de betalingen direct moet
    uitbetalen vanuit de eigen bankrekening, voordat hij de status op
    verwerkt zet. Autorisatie: hasAnyAuthority("ADMIN", "EMPLOYEE")

@PUT API/v1/employee/id={Long employeeId}/order-id={Long orderId}
Functie: Voegt een bestelling toe aan de employee zijn order lijst die
hij dan vervolgens kan verwerken. (deze functie is niet perse nodig
omdat alle orders direct verdeeld kunnen worden over alle werknemers met
een ander endpoint.) Autorisatie: hasAuthority("ADMIN")

@PUT API/v1/employee/id={Long employeeId}/order-id={Long orderId}
Functie: Voegt retouren toe aan de retouren lijst van de employee.
Autorisatie: hasAuthority("ADMIN")

-   @PUT API/v1/employee/divide-orders Functie: Verdeelt alle nog niet
    verwerkte bestellingen over alle werknemers! Stel er zijn 200
    bestellingen en 10 werknemers dan krijgt elke werknemer 20
    bestellingen aan zijn lijst toegevoegd. Autorisatie:
    hasAuthority("ADMIN")

-   @PUT API/v1/employee/divide-returns Functie: Doet hetzelfde als het
    endpoint hierboven maar dan met niet verwerkte retouren.
    Autorisatie: hasAuthority("ADMIN")

Slot: Deze installatiehandleiding biedt hulp bij het opstarten en
gebruiken van de applicatie! Ik heb veel geleerd van het bouwen van deze
applicatie en er ook een hoop plezier aan gehad! Heel erg veel succes
met nakijken!

Gemaakt door William Meester
