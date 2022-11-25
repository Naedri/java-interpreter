Programmation en Java d’interprètes pour un sous ensemble de Java 8
===

Auteurs : JALLAIS Adrien & SIMON Géraud
Date de rendu : 25/11/2022

# Sommmaire

[TOC]

# Checklist rendu

- [ ] diagramme uml => uml.pdf
- [ ] archive eclipe => 2022-2023-FILA3-Capitrain-NOM1-Prenom1-NOM2-Prenom2.zip
- [ ] rapport => rapport_NOM1_Prenom1_NOM2_Prenom2.pdf
- [ ] vos nos et prenoms
- [ ] organisation du code et ce qu’il faut lancer pour executer votre programme (1/2 page)
- [ ] une liste de ce que vous avez fait (1/2 page)
- [ ] une liste de ce que vous n’avez pas fait (1/2 page)
- [ ] une liste des points forts de votre projet (1/2 page)
- [ ] une liste des points faibles de votre projet  (1/2 page)
- [ ] un choix discute : (1/2 a 1 page)
  - [ ] option 1 : avantages, inconvenients
  - [ ] option 2 : avantages, inconvenients
  - [ ] choix : explication de pourquoi l’option1 a ete choisie plutot que l’option2
- [ ] ce que vous feriez differement si c’etait a refaire (1/2 page)
- [ ] et tout ce qui pourra m’aider a evaluer votre projet (XX pages)
- [ ] le log du projet : c’est a dire pour chaque demi journee ce qui a ete prevu de faire, ce qui effectivement ete fait

# Introduction

Dans le cadre d'une unité d'ensignement d'initiation à la recherche, Rémi Douence a proposé de s'intéresser à une définition rigoureuse des expressions lambdas intégrées par Java 8, avec le papier suivant : [Property-based Testing for Lambda-Expressions Semantics in Featherweight Java. (S. da Silva Feitosa,2018)](https://doi.org/10.1145/3264637.3264643). Dans cet article, les auteurs formalisent cette nouvelle fonctionnalité en utilisant des interprètes en Haskell.

L'objectif de ce projet est de convertir ces interprètes en Java et de démontrer le bon fonctionnement de nos interprètes en Java.

# Description du code

Pour faciliter la compréhension globale du code, un [diagramme de classe a été réalisé](https://github.com/Naedri/java-interpreter/blob/main/diagram/java-interpreter-class-diagram.png).

## Comment le code est-il organisé ?

## Comment le code peut-il être lancé ?

Le code de ce projet peut être exécuté en utilisant la fonction `main` du fichier `./src/main.java`.

# Résultats

## Quelles méthodes en Haskell, ont été converties en Java ?

| Column 1 | Column 2 | Column 3 |
| -------- | -------- | -------- |
| Text     | Text     | Text     |

- Traduction du code
  - FJParser = 100% (cf. src.Parser.Definition et cf. src.Parser.Expression)
  - FJUtils = 50 % (cf. src.Utils.FJUtils)
    - Fonctions traduites :
      - subtyping()
      - fields()
      - methods()
      - isValue()
    - Fonctions non traduites ou partiellement :
      - absmethods()
      - mtype()
      - mbody()
      - lambdaMark()
  - FJInterpreter = 30% (cf. src.V1.FJInterpreter)
    - Eval'() partiellement terminée
    - eval() et subst() non traduites
- Une base de test             ADRIEN TODO
- Notes et synthèse de l'article de recherche

## Quelles méthodes en Haskell, reste-il à convertir en Java ?

| Méthodes    | Développée         | Testée                        |
| ----------- | ------------------ | ----------------------------- |
| `totototoo` | :white_check_mark: | :negative_squared_cross_mark: |
| `toto`      | :white_check_mark: | :negative_squared_cross_mark: |
| `toto`      | :white_check_mark: | :negative_squared_cross_mark: |
| `toto`      | :white_check_mark: | :negative_squared_cross_mark: |
| `toto`      | :white_check_mark: | :negative_squared_cross_mark: |

# Discussion

## Quels sont les points forts et faibles de notre interprète en Java

### Points forts

CT en singleton

### Points faibles

Seule l'approche V1 a pu être explorée et traduite dans notre projet, faute de temps.

## Choix réalisés

Comme le montre la liste suivante, plusieurs choix on pu être réalisés lors de l'implémentation des interpréteurs.

-
- Utilisation de structures de représentation des séquences

Dans la partie suivante nous discuterons de ce dernier choix.

### Structures de représentation des séquences

Les séquences ($\overline{T}$)

#### options 1

| Avantages | Inconvénient |
| --------- | ------------ |
|           |              |
|           |              |
|           |              |
|           |              |

#### options 2

| Avantages | Inconvénient |
| --------- | ------------ |
|           |              |
|           |              |
|           |              |
|           |              |

#### conlusion

**les structures de données (Array, List, Set)**

Dans différentes classes et méthodes, nous utilisons des Structures de données. Dans chaque cas, nous avons d'abord vérifié si l'objet lié allait être modifié ou non, et si nous avions besoins de tableaux ou de listes.

Notre première version du projet utilisait des **tableaux** pour les structures de données (ex: en attribut de la classe Signature, on avait "Field[] params").
Ce choix avait été pris pour les cas où nous étions sûrs que les données étaient définies, et donc que la taille du tableau serait fixe.

La seconde version du projet utilisait des **listes** (List puis ArrayList).
L'objectif principal était de limiter la possiblité d'un ArrayOutOfBound (dépassement de la taille du tableau quand on le parcoure ou quand on y ajoute un nouvel élément). Ensuite, les listes permettaient une navigation plus simple dans les parcours, comme dans les for, avec plus de flexibilité que les tableaux.
Une première implémentation était l'utilisation du type List<> dans les signatures des méthodes, puis dans l'implémentation un ArrayList<>. Cela offre un code plus sûr et plus adapté aux pratiques professionnelles. Cependant, pour simplifier temporairement le code, nous sommes passés à utiliser ArrayList<> à chaque fois.

La troisième et actuelle version utilise des **sets** (TreeSet).
L'objectif était de protéger nos listes en supprimant les doublons.

Linked list est une surcouche non nécessaire pour garder l'avantage ordonnée ou triée.

# Rétrospectives

Dans le cas où nous devrions refire ce projet d'initiation à la recherche,

# Annexes

## [Fichier de notes](https://pad.faire-ecole.org/s/QrN2T4Tq_#)

### Lexique

### Interprétations du code Haskell

## [Fichier de log](https://pad.faire-ecole.org/s/suGTsvbBS#)
