# EXPLICATION DETAILLEE - Spring MVC - Thymeleaf

## Table des matieres

1. [Introduction](#introduction)
2. [Technologies utilisees](#technologies-utilisees)
3. [Architecture du projet](#architecture-du-projet)
4. [Explication de Thymeleaf](#explication-de-thymeleaf)
5. [Comparaison JSP vs Thymeleaf](#comparaison-jsp-vs-thymeleaf)
6. [Configuration Spring MVC avec Thymeleaf](#configuration-spring-mvc-avec-thymeleaf)
7. [Syntaxe Thymeleaf en detail](#syntaxe-thymeleaf-en-detail)
8. [Couche Controller](#couche-controller)
9. [Base de donnees](#base-de-donnees)
10. [Vues Thymeleaf](#vues-thymeleaf)
11. [Design CSS avec Bootstrap](#design-css-avec-bootstrap)
12. [Comparaison avec les 2 autres projets](#comparaison-avec-les-2-autres-projets)
13. [Conclusion](#conclusion)

---

## Introduction

Ce projet est une application de **gestion de contacts** developpee avec **Spring MVC** et **Thymeleaf**. Il demontre une approche moderne de templates HTML avec une integration native avec Spring.

### Objectifs pedagogiques

- Comprendre les avantages de **Thymeleaf** par rapport a JSP
- Apprendre la syntaxe `th:` de Thymeleaf
- Utiliser des **templates HTML naturels**
- Integrateur **Bootstrap via Webjars**
- Creer des interfaces modernes et **responsive**

---

## Technologies utilisees

| Technologie | Version | Role |
|-------------|---------|------|
| **Java** | 11 | Langage de programmation |
| **Spring MVC** | 5.2.22.RELEASE | Framework web |
| **Thymeleaf** | 3.0.15.RELEASE | Moteur de template HTML |
| **Hibernate** | 5.4.10.Final | ORM |
| **MySQL** | 8.x | Base de donnees |
| **Bootstrap** | 5.2.0 (Webjars) | Framework CSS |
| **Maven** | 3.9.x | Outil de build |

---

## Architecture du projet

### Architecture avec Thymeleaf

```
┌─────────────────────────────────────────────────────────────┐
│                   VUES THYMELEAF (HTML)                      │
│  index.html | contacts.html | groupes.html | add-*.html     │
│                                                             │
│  Templates HTML naturels avec attributs th:                 │
│  th:text, th:each, th:href, th:if, th:unless               │
└─────────────────────────────────────────────────────────────┘
                            │
                            ▼
┌─────────────────────────────────────────────────────────────┐
│              SPRING TEMPLATE ENGINE                          │
│  ServletContextTemplateResolver -> SpringTemplateEngine     │
│  Resout les templates et remplace les attributs th:         │
└─────────────────────────────────────────────────────────────┘
                            │
                            ▼
┌─────────────────────────────────────────────────────────────┐
│                  CONTROLLER (Spring MVC)                     │
│          ContactController.java | GroupeController.java     │
└─────────────────────────────────────────────────────────────┘
                            │
                            ▼
┌─────────────────────────────────────────────────────────────┐
│                    SERVICE / DAO                             │
│        ContactService | GroupeService | DAO generique       │
└─────────────────────────────────────────────────────────────┘
                            │
                            ▼
┌─────────────────────────────────────────────────────────────┐
│                BASE DE DONNEES (MySQL)                       │
│                   tables: contacts, groupes                  │
└─────────────────────────────────────────────────────────────┘
```

### Structure des dossiers

```
springmvc-contacts/
├── pom.xml
├── src/main/
│   ├── java/com/contact/app/
│   │   ├── config/
│   │   │   └── SpringWebConfig.java           # Config Thymeleaf
│   │   ├── controller/
│   │   │   ├── ContactController.java
│   │   │   └── GroupeController.java
│   │   ├── entities/
│   │   │   ├── ContactEntity.java
│   │   │   └── GroupeEntity.java
│   │   ├── dto/
│   │   │   ├── ContactDto.java
│   │   │   └── GroupeDto.java
│   │   ├── dao/
│   │   │   ├── IContactDao.java / ContactDao.java
│   │   │   └── IGroupeDao.java / GroupeDao.java
│   │   ├── mapper/
│   │   │   └── ContactMapper.java
│   │   └── service/
│   │       ├── ContactService.java
│   │       └── GroupeService.java
│   └── webapp/
│       ├── WEB-INF/views/thymeleaf/
│       │   ├── index.html                     # Tableau de bord
│       │   ├── contacts.html                  # Liste des contacts
│       │   ├── add-contact.html               # Ajouter/Modifier
│       │   ├── groupes.html                   # Liste des groupes
│       │   └── add-groupe.html                # Ajouter/Modifier
│       └── resources/core/css/
│           └── main.css                       # CSS personnalise
```

---

## Explication de Thymeleaf

### Qu'est-ce que Thymeleaf ?

**Thymeleaf** est un moteur de template HTML **naturel** qui remplace JSP. Ses templates sont des **fichiers HTML valides** qui peuvent etre visualises dans un navigateur sans serveur.

### Caracteristiques principales

1. **HTML naturel** : Les templates sont du HTML valide
2. **Attributs speciaux** : Les instructions utilisent des attributs `th:`
3. **Spring EL** : Integration native avec Spring Expression Language
4. **Testable** : Les templates peuvent etre testes hors serveur
5. **Separation** : Le designer peut travailler sur les HTML sans connaitre Java

### Exemple de base

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title th:text="${titre}">Titre par defaut</title>
</head>
<body>
    <h1 th:text="${titre}">Titre par defaut</h1>
    <p th:if="${description != null}" th:text="${description}"></p>
    
    <ul>
        <li th:each="element : ${liste}" th:text="${element}"></li>
    </ul>
</body>
</html>
```

---

## Comparaison JSP vs Thymeleaf

| Critere | JSP | Thymeleaf |
|---------|-----|-----------|
| **Type de fichier** | `.jsp` | `.html` |
| **HTML valide** | Non (contient du Java) | **Oui** (naturel) |
| **Syntaxe** | `<% %>` scriptlets | **`th:text`** attributs |
| **Tags** | `<c:forEach>` JSTL | **`th:each`** |
| **Conditions** | `<c:if>` | **`th:if`**, `th:unless` |
| **Spring EL** | Limite `${var}` | **`th:text="${var}"`** |
| **URL** | `${pageContext.request.contextPath}` | **`@{/url}`** |
| **Visualisation** | Impossible sans serveur | **Possible** dans navigateur |
| **Test** | Necessite un serveur | **Testable** hors serveur |
| **Maintenance** | Difficile | **Facile** |
| **Design** | Impossible | **Possible** avec un outil |

### Exemple comparatif

**JSP (ancienne approche) :**
```jsp
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head><title>${titre}</title></head>
<body>
    <c:forEach var="contact" items="${contacts}">
        <div>
            <h3>${contact.nom}</h3>
            <p>${contact.email}</p>
            <a href="${pageContext.request.contextPath}/contacts/edit/${contact.id}">
                Modifier
            </a>
        </div>
    </c:forEach>
</body>
</html>
```

**Thymeleaf (approche moderne) :**
```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head><title th:text="${titre}">Contacts</title></head>
<body>
    <div th:each="contact : ${contacts}">
        <h3 th:text="${contact.nom}"></h3>
        <p th:text="${contact.email}"></p>
        <a th:href="@{/contacts/edit/{id}(id=${contact.id})}">Modifier</a>
    </div>
</body>
</html>
```

---

## Configuration Spring MVC avec Thymeleaf

### SpringWebConfig.java

```java
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.contact.app")
public class SpringWebConfig implements WebMvcConfigurer {

    @Autowired
    private ServletContext servletContext;

    // 1. Template Resolver
    @Bean
    public ServletContextTemplateResolver templateResolver() {
        ServletContextTemplateResolver resolver = 
            new ServletContextTemplateResolver(servletContext);
        resolver.setPrefix("/WEB-INF/views/thymeleaf/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode("HTML");
        resolver.setCacheable(false);
        return resolver;
    }

    // 2. Template Engine
    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.setTemplateResolver(templateResolver());
        return engine;
    }

    // 3. View Resolver
    @Bean
    public ThymeleafViewResolver viewResolver() {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(templateEngine());
        resolver.setCharacterEncoding("UTF-8");
        return resolver;
    }
}
```

### Comment fonctionne Thymeleaf avec Spring ?

```
Controller retourne "contacts"
         │
         ▼
┌─────────────────────────────────────────┐
│         ThymeleafViewResolver           │
│  Cherche contacts.html dans             │
│  /WEB-INF/views/thymeleaf/              │
├─────────────────────────────────────────┤
│  SpringTemplateEngine :                 │
│  - Parse le HTML                         │
│  - Remplace les attributs th:           │
│  - Execute les instructions Spring EL   │
├─────────────────────────────────────────┤
│  Rendu HTML final envoye au client      │
└─────────────────────────────────────────┘
```

---

## Syntaxe Thymeleaf en detail

### 1. `th:text` - Afficher du texte

```html
<h1 th:text="${titre}">Titre par defaut</h1>
<p th:text="${contact.nom}"></p>
<span th:text="${contact.email}"></span>
```

### 2. `th:each` - Boucle

```html
<tr th:each="contact : ${contacts}">
    <td th:text="${contact.id}"></td>
    <td th:text="${contact.nom}"></td>
    <td th:text="${contact.email}"></td>
</tr>
```

### 3. `th:if` et `th:unless` - Conditions

```html
<!-- Afficher si la condition est vraie -->
<div th:if="${contacts.isEmpty()}">
    <p>Aucun contact</p>
</div>

<!-- Afficher si la condition est fausse -->
<div th:unless="${contacts.isEmpty()}">
    <table>...</table>
</div>
```

### 4. `th:href` - Liens dynamiques

```html
<a th:href="@{/}">Accueil</a>
<a th:href="@{/contacts}">Contacts</a>
<a th:href="@{/contacts/edit/{id}(id=${contact.id})}">Modifier</a>
```

### 5. `th:src` - Images et scripts

```html
<script th:src="@{/webjars/bootstrap/5.2.0/js/bootstrap.bundle.min.js}"></script>
<link rel="stylesheet" th:href="@{/webjars/bootstrap/5.2.0/css/bootstrap.min.css}"/>
```

### 6. `th:action` - Formulaires

```html
<form th:action="${contact.id != null} ? @{/contacts/update} : @{/contacts/save}" method="post">
    <input type="hidden" th:if="${contact.id != null}" name="id" th:value="${contact.id}"/>
    <input type="text" name="nom" th:value="${contact.nom}" required/>
</form>
```

### 7. `th:field` - Liaison de champs

```html
<select name="groupeId">
    <option value="">-- Choisir --</option>
    <option th:each="groupe : ${groupes}" 
            th:value="${groupe.id}" 
            th:text="${groupe.nom}"
            th:selected="${contact.groupeId == groupe.id}">
    </option>
</select>
```

---

## Couche Controller

### ContactController.java

```java
@Controller
public class ContactController {

    private final ContactService contactService = new ContactService();
    private final GroupeService groupeService = new GroupeService();

    @GetMapping("/")
    public String dashboard(Model model) {
        model.addAttribute("contacts", contactService.findAll());
        model.addAttribute("totalContacts", contactService.findAll().size());
        model.addAttribute("totalGroupes", groupeService.findAll().size());
        return "index";  // Resolu vers index.html
    }

    @GetMapping("/contacts")
    public String list(@RequestParam(value = "groupeId", required = false) Long groupeId,
                       Model model) {
        if (groupeId != null) {
            model.addAttribute("contacts", contactService.findAll().stream()
                .filter(c -> groupeId.equals(c.getGroupeId()))
                .collect(Collectors.toList()));
        } else {
            model.addAttribute("contacts", contactService.findAll());
        }
        model.addAttribute("groupes", groupeService.findAll());
        return "contacts";
    }

    @GetMapping("/contacts/add")
    public String showAddForm(Model model) {
        model.addAttribute("contact", new ContactDto());
        model.addAttribute("groupes", groupeService.findAll());
        return "add-contact";
    }

    @PostMapping("/contacts/save")
    public String save(@RequestParam("nom") String nom,
                       @RequestParam(value = "email", required = false) String email,
                       @RequestParam(value = "groupeId", required = false) Long groupeId) {
        ContactDto dto = new ContactDto();
        dto.setNom(nom);
        dto.setEmail(email);
        dto.setGroupeId(groupeId);
        contactService.save(dto);
        return "redirect:/contacts";
    }
}
```

---

## Base de donnees

### Script SQL

```sql
CREATE DATABASE IF NOT EXISTS contacts_db;

CREATE TABLE groupes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    description TEXT
);

CREATE TABLE contacts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100),
    email VARCHAR(200),
    telephone VARCHAR(20),
    adresse TEXT,
    groupe_id BIGINT,
    FOREIGN KEY (groupe_id) REFERENCES groupes(id)
);
```

### Diagramme de relations

```
┌─────────────────┐       ┌─────────────────┐
│     GROUPES     │       │    CONTACTS     │
├─────────────────┤       ├─────────────────┤
│ id (PK)         │◄──────│ groupe_id (FK)  │
│ nom             │   1,N │ id (PK)         │
│ description     │       │ nom             │
└─────────────────┘       │ prenom          │
                          │ email           │
                          │ telephone       │
                          │ adresse         │
                          └─────────────────┘
```

---

## Vues Thymeleaf

### Exemple complet : contacts.html

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org" lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Liste des Contacts</title>
    <link rel="stylesheet" th:href="@{/webjars/bootstrap/5.2.0/css/bootstrap.min.css}">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.9.1/font/bootstrap-icons.css">
    <style>
        body { background-color: #f0f2f5; }
        .navbar-brand-custom { background: linear-gradient(135deg, #11998e 0%, #38ef7d 100%); }
        .card { border: none; border-radius: 12px; }
        .btn-gradient { background: linear-gradient(135deg, #11998e 0%, #38ef7d 100%); border: none; color: white; }
        .contact-avatar { width: 40px; height: 40px; border-radius: 50%; display: flex; align-items: center; justify-content: center; font-weight: bold; color: white; }
    </style>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark navbar-brand-custom">
        <div class="container">
            <a class="navbar-brand fw-bold" th:href="@{/}">
                <i class="bi bi-person-lines-fill"></i> Contacts App
            </a>
            <div class="navbar-nav ms-auto">
                <a class="nav-link" th:href="@{/}">Accueil</a>
                <a class="nav-link active" th:href="@{/contacts}">Contacts</a>
                <a class="nav-link" th:href="@{/groupes}">Groupes</a>
            </div>
        </div>
    </nav>

    <div class="container">
        <div class="d-flex justify-content-between align-items-center mb-4 mt-4">
            <h2><i class="bi bi-people"></i> Contacts</h2>
            <a th:href="@{/contacts/add}" class="btn btn-gradient">
                <i class="bi bi-plus-lg"></i> Nouveau Contact
            </a>
        </div>

        <div class="card shadow-sm">
            <div class="card-body">
                <div th:if="${#lists.isEmpty(contacts)}" class="text-center py-5">
                    <i class="bi bi-inbox display-1 text-muted"></i>
                    <p class="mt-3 fs-5 text-muted">Aucun contact trouve</p>
                </div>

                <div th:unless="${#lists.isEmpty(contacts)}" class="table-responsive">
                    <table class="table table-hover align-middle">
                        <thead>
                            <tr>
                                <th>#</th>
                                <th>Nom</th>
                                <th>Email</th>
                                <th>Telephone</th>
                                <th>Groupe</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr th:each="contact : ${contacts}">
                                <td th:text="${contact.id}"></td>
                                <td>
                                    <div class="d-flex align-items-center">
                                        <div class="contact-avatar bg-success me-2" 
                                             th:text="${#strings.substring(contact.prenom,0,1) + #strings.substring(contact.nom,0,1)}">
                                        </div>
                                        <span class="fw-semibold" 
                                              th:text="${contact.nom + ' ' + contact.prenom}">
                                        </span>
                                    </div>
                                </td>
                                <td th:text="${contact.email}"></td>
                                <td th:text="${contact.telephone}"></td>
                                <td>
                                    <span class="badge bg-success" 
                                          th:text="${contact.groupeNom != null ? contact.groupeNom : 'Aucun'}">
                                    </span>
                                </td>
                                <td>
                                    <a th:href="@{/contacts/edit/{id}(id=${contact.id})}" 
                                       class="btn btn-sm btn-outline-warning">
                                        <i class="bi bi-pencil"></i>
                                    </a>
                                    <a th:href="@{/contacts/delete/{id}(id=${contact.id})}" 
                                       class="btn btn-sm btn-outline-danger"
                                       onclick="return confirm('Supprimer?')">
                                        <i class="bi bi-trash"></i>
                                    </a>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <script th:src="@{/webjars/bootstrap/5.2.0/js/bootstrap.bundle.min.js}"></script>
</body>
</html>
```

---

## Design CSS avec Bootstrap

### Gradients utilises

```css
/* Navbar et boutons */
.navbar-brand-custom {
    background: linear-gradient(135deg, #11998e 0%, #38ef7d 100%);
}

.btn-gradient {
    background: linear-gradient(135deg, #11998e 0%, #38ef7d 100%);
    border: none;
    color: white;
}

/* Cartes statistiques */
.stat-card {
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    color: white;
}

/* Avatars */
.contact-avatar {
    width: 40px;
    height: 40px;
    border-radius: 50%;
    display: flex;
    align-items: center;
    justify-content: center;
    font-weight: bold;
    color: white;
    background: linear-gradient(135deg, #11998e 0%, #38ef7d 100%);
}
```

### Integration Bootstrap via Webjars

```html
<!-- Dans les templates Thymeleaf -->
<link rel="stylesheet" th:href="@{/webjars/bootstrap/5.2.0/css/bootstrap.min.css}"/>
<script th:src="@{/webjars/bootstrap/5.2.0/js/bootstrap.bundle.min.js}"></script>

<!-- Dans le pom.xml -->
<dependency>
    <groupId>org.webjars</groupId>
    <artifactId>bootstrap</artifactId>
    <version>5.2.0</version>
</dependency>
```

---

## Comparaison avec les 2 autres projets

| Critere | Projet 1 (Todo) | Projet 2 (Bibliotheque) | Projet 3 (Contacts) |
|---------|-----------------|-------------------------|---------------------|
| **Technologie** | JSP + JSTL | JSP + JSTL + Tiles | **Thymeleaf** |
| **HTML valide** | Non | Non | **Oui** |
| **Layout** | Non | Tiles | Non (mais possible) |
| **Separation** | Mauvaise | Meilleure | **Excellente** |
| **Design** | Bootstrap CDN | Bootstrap CDN | **Bootstrap Webjars** |
| **Templates** | `.jsp` | `.jsp` + tiles.xml | **`.html`** |
| **Modernite** | Ancienne | Ancienne + Layout | **Moderne** |

---

## Conclusion

Ce projet demontre les avantages de **Thymeleaf** par rapport a JSP :

1. **HTML naturel** : Les templates sont du HTML valide
2. **Separation** : Le code HTML et les instructions sont separes
3. **Spring EL** : Integration native avec Spring Expression Language
4. **Webjars** : Gestion moderne des dependances cote client
5. **Design** : Interfaces modernes et responsive

### Avantages de Thymeleaf
- Templates visualisables dans un navigateur
- Testable hors serveur
- Code plus propre et plus maintenable
- Separation parfaite HTML/Java

### Vers quoi aller ?
- **Layout dialect** : Ajouter un layout a Thymeleaf
- **Spring Boot** : Simplifier la configuration
- **REST API** : Creer des API REST avec `@RestController`
