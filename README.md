# Spring MVC - Contacts (Thymeleaf)

[![Java](https://img.shields.io/badge/Java-11-green)](https://www.oracle.com/java/)
[![Spring MVC](https://img.shields.io/badge/Spring%20MVC-5.2.22-yellowgreen)](https://spring.io/projects/spring-framework)
[![Thymeleaf](https://img.shields.io/badge/Thymeleaf-3.0.15-brightgreen)](https://www.thymeleaf.org/)
[![MySQL](https://img.shields.io/badge/MySQL-8.x-blue)](https://www.mysql.com/)
[![Bootstrap](https://img.shields.io/badge/Bootstrap-5.2-purple)](https://getbootstrap.com/)

## Description

Application de gestion de contacts developpee avec Spring MVC et Thymeleaf. Cette application permet de gerer des contacts avec des groupes (Famille, Amis, Travail, etc.), avec un design moderne et responsive grace a Thymeleaf et Bootstrap.

### Capture d'ecran de l'interface

![Dashboard](screenshots/dashboard.png)
*Tableau de bord avec statistiques*

![Liste des contacts](screenshots/contacts.png)
*Liste des contacts avec avatars*

![Groupes](screenshots/groupes.png)
*Gestion des groupes en cartes*

---

## Environnement

| Outil | Version |
|-------|---------|
| JDK | 11 |
| Tomcat | 9.x |
| Maven | 3.9.x |
| Spring MVC | 5.2.22.RELEASE |
| Hibernate | 5.4.10.Final |
| Thymeleaf | 3.0.15.RELEASE |
| MySQL | 8.x |
| Bootstrap | 5.2.0 (Webjars) |

---

## Architecture du projet

```
springmvc-contacts/
├── pom.xml
├── src/main/
│   ├── java/com/contact/app/
│   │   ├── MyServletInitializer.java
│   │   ├── config/
│   │   │   ├── SpringWebConfig.java          # Config Thymeleaf
│   │   │   ├── HibernateUtil.java
│   │   │   └── PropertiesReader.java
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
│   │   │   ├── Repository.java
│   │   │   ├── RepositoryImpl.java
│   │   │   ├── IContactDao.java / ContactDao.java
│   │   │   └── IGroupeDao.java / GroupeDao.java
│   │   ├── mapper/
│   │   │   └── ContactMapper.java
│   │   └── service/
│   │       ├── ContactService.java
│   │       └── GroupeService.java
│   └── webapp/
│       ├── WEB-INF/views/thymeleaf/
│       │   ├── index.html
│       │   ├── contacts.html
│       │   ├── add-contact.html
│       │   ├── groupes.html
│       │   └── add-groupe.html
│       └── resources/core/css/
│           └── main.css
```

---

## Configuration Thymeleaf

### SpringWebConfig.java

```java
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.contact.app")
public class SpringWebConfig implements WebMvcConfigurer {

    @Bean
    public ServletContextTemplateResolver templateResolver() {
        ServletContextTemplateResolver resolver = new ServletContextTemplateResolver();
        resolver.setPrefix("/WEB-INF/views/thymeleaf/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode("HTML");
        return resolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.setTemplateResolver(templateResolver());
        return engine;
    }

    @Bean
    public ThymeleafViewResolver viewResolver() {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(templateEngine());
        return resolver;
    }
}
```

### Difference avec JSP

| Aspect | JSP | Thymeleaf |
|--------|-----|-----------|
| Syntaxe | `<% %>` scriptlets | `th:text` attributs HTML |
| Tags | `<c:forEach>` JSTL | `th:each` naturel |
| Validation | Pas de validation | HTML valide |
| Spring EL | Limite | `th:text="${var}"` |
| Cache | Compile | Template cache |

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

---

## Exemples de code Thymeleaf

### Boucle avec th:each

```html
<tr th:each="contact : ${contacts}">
    <td th:text="${contact.id}"></td>
    <td>
        <span th:text="${contact.nom + ' ' + contact.prenom}"></span>
    </td>
    <td th:text="${contact.email}"></td>
</tr>
```

### Condition avec th:if

```html
<div th:if="${#lists.isEmpty(contacts)}">
    <p>Aucun contact</p>
</div>
<div th:unless="${#lists.isEmpty(contacts)}">
    <!-- Liste -->
</div>
```

### Formulaires

```html
<form th:action="${contact.id != null} ? @{/contacts/update} : @{/contacts/save}" method="post">
    <input type="hidden" th:if="${contact.id != null}" name="id" th:value="${contact.id}"/>
    <input type="text" name="nom" th:value="${contact.nom}" required/>
    
    <select name="groupeId">
        <option value="">-- Choisir --</option>
        <option th:each="groupe : ${groupes}" 
                th:value="${groupe.id}" 
                th:text="${groupe.nom}"
                th:selected="${contact.groupeId == groupe.id}">
        </option>
    </select>
</form>
```

### Liaison CSS avec Webjars

```html
<link rel="stylesheet" th:href="@{/webjars/bootstrap/5.2.0/css/bootstrap.min.css}"/>
<script th:src="@{/webjars/bootstrap/5.2.0/js/bootstrap.bundle.min.js}"></script>
```

---

## Installation

```bash
git clone https://github.com/votre-username/springmvc-contacts.git
cd springmvc-contacts

# Configurer database.properties
mvn clean install

cp target/springmvc-contacts.war $TOMCAT_HOME/webapps/
```

---

## Routes disponibles

| Methode | URL | Description |
|---------|-----|-------------|
| GET | `/` | Tableau de bord |
| GET | `/contacts` | Liste des contacts |
| GET | `/contacts/add` | Ajouter un contact |
| POST | `/contacts/save` | Enregistrer |
| GET | `/contacts/edit/{id}` | Modifier |
| GET | `/contacts/delete/{id}` | Supprimer |
| GET | `/groupes` | Liste des groupes |
| GET | `/groupes/add` | Ajouter un groupe |
| GET | `/contacts?groupeId={id}` | Filtrer par groupe |

---

## Points cles

### Avantages de Thymeleaf vs JSP

1. **HTML naturel** : Les templates sont des HTML valides, visualisables dans un navigateur
2. **Spring EL** : Integration native avec Spring Expression Language
3. **Pas de scriptlets** : Pas de code Java dans les vues
4. **Testable** : Les templates peuvent etre testes hors serveur
5. **Separation** : Le designer peut travailler sur les HTML sans connaitre Java

### Design avec CSS

L'application utilise des gradients modernes :
- **Navbar** : `linear-gradient(135deg, #11998e 0%, #38ef7d 100%)`
- **Boutons** : Meme gradient pour l'harmonie visuelle
- **Cartes** : Border-radius arrondi et ombres subtiles
- **Avatars** : Initiales du contact dans un cercle colore

---

## Technologies

- **Spring MVC 5.2.22** : Framework web
- **Thymeleaf 3.0.15** : Moteur de template naturel
- **Hibernate 5.4.10** : ORM
- **MySQL 8** : Base de donnees
- **Bootstrap 5.2** : Framework CSS (via Webjars)

---

## Auteur

Developpe avec Spring MVC - Thymeleaf
