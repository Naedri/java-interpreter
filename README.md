Programmation en Java d’interprètes pour un sous ensemble de Java 8
===

- Auteurs : JALLAIS Adrien & SIMON Géraud
- Promotion : IMT FIL 2023 - 3ème année
- Date de rendu : 25/11/2022

# Sommmaire

[TOC]

# Introduction

Dans le cadre d'une unité d'ensignement d'initiation à la recherche, Rémi Douence a proposé de s'intéresser à une définition rigoureuse des expressions lambdas intégrées par Java 8, avec le papier suivant : [Property-based Testing for Lambda-Expressions Semantics in Featherweight Java. (S. da Silva Feitosa,2018)](https://doi.org/10.1145/3264637.3264643). Dans cet article, les auteurs formalisent cette nouvelle fonctionnalité en utilisant des interprètes en Haskell.

L'objectif de ce projet est de convertir ces interprètes en Java et de démontrer le bon fonctionnement de nos interprètes en Java.

# Description du code

Pour faciliter la compréhension globale du code, un [diagramme de classe a été réalisé](https://github.com/Naedri/java-interpreter/blob/main/diagram/java-interpreter-class-diagram.png).

## Comment le code est-il organisé ?

Le code de ce projet est organisé de la manière suivante :

- Les différentes structures de données de `FJParser.hs` ont été répartis dans le package `src.Parser.DefinitionP`, sauf pour `Expr` et `TypeError`.
- Les expressions (`Expr`) ont été regroupées dans le package `src.Parser.ExpressionP` et héritent toutes de la classe abstraire java : `Expr`.
- Les différents types d'erreurs (`TypeError`) ont été regroupées dans le package `src.Parser.TypingErrorP`.
- Le package `src.Utils` contient la classe `FJUtils`, avec les méthodes générales d'évaluation.
- Notre traduction de l'approche V1 ne comprend à l'heure actuelle que la traduction de `FJInterpreter.hs`. Celle-ci est disponible dans le package `src.V1`
  - Remarque : Pour `FJInterpreter.java`, les méthodes `evalPrime()` et `subst()` sont accompagnées de "sous-méthodes" correspondantes au *pattern matching* disponible en Haskell selon les types de paramètres (ex: `eval' ct (CreateObject c p)` est traduit en Java par la méthode `evalPrime()` qui va appeler `evalPrimeAsCreateObject()`).

## Comment le code peut-il être lancé ?

Le code de ce projet peut être exécuté en utilisant la fonction `main` du fichier `./src/main.java`.
Sous Eclipse, ce projet peut être importé après avoir dezippé le dossier obtenu depuis github, puis après avoir réalisé les actions suivantes : `File` > `Import` > `General` > `Import` > `Existing Projects into Workspace` > `Select root directory` en sélectionnant comme racine le contenu du dossier décompressé > `Finish`.

# Résultats

## Quelles méthodes en Haskell, ont été converties en Java ?

| Méthodes                       | Première implémentation | Testée                        |
| ------------------------------ | ----------------------- | ----------------------------- |
| **`FJParser`**                 |                         |                               |
| `T.TClass`                     | :white_check_mark:      | :white_check_mark:            |
| `T.TInterface`                 | :white_check_mark:      | :white_check_mark:            |
| `Class`                        | :white_check_mark:      | :white_check_mark:            |
| `Interface`                    | :white_check_mark:      | :negative_squared_cross_mark: |
| `Method`                       | :white_check_mark:      | :white_check_mark:            |
| `Expr.Var`                     | :white_check_mark:      | :white_check_mark:            |
| `Expr.FieldAccess`             | :white_check_mark:      | :white_check_mark:            |
| `Expr.MethodInvk`              | :white_check_mark:      | :negative_squared_cross_mark: |
| `Expr.CreateObject`            | :white_check_mark:      | :white_check_mark:            |
| `Expr.Cast`                    | :white_check_mark:      | :negative_squared_cross_mark: |
| `Expr.Closure`                 | :white_check_mark:      | :negative_squared_cross_mark: |
| `Type`                         | :white_check_mark:      | :white_check_mark:            |
| `Env`                          | :white_check_mark:      | :negative_squared_cross_mark: |
| `CT`                           | :white_check_mark:      | :white_check_mark:            |
| `TypeError.VariableNotFound`   | :white_check_mark:      | :negative_squared_cross_mark: |
| `TypeError.FieldNotFound`      | :white_check_mark:      | :negative_squared_cross_mark: |
| `TypeError.ClassNotFound`      | :white_check_mark:      | :negative_squared_cross_mark: |
| `TypeError.MethodNotFound`     | :white_check_mark:      | :negative_squared_cross_mark: |
| `TypeError.ParamsTypeMismatch` | :white_check_mark:      | :negative_squared_cross_mark: |
| `TypeError.WrongClosure`       | :white_check_mark:      | :negative_squared_cross_mark: |
| `TypeError.WrongCast`          | :white_check_mark:      | :negative_squared_cross_mark: |
| `TypeError.UnknownError`       | :white_check_mark:      | :negative_squared_cross_mark: |
| **`FJUtils`**                  |                         |                               |
| `subtyping`                    | :white_check_mark:      | :negative_squared_cross_mark: |
| `fields`                       | :white_check_mark:      | :negative_squared_cross_mark: |
| `methods`                      | :white_check_mark:      | :negative_squared_cross_mark: |
| `mbody`                        | :white_check_mark:      | :negative_squared_cross_mark: |
| `isValue`                      | :white_check_mark:      | :negative_squared_cross_mark: |
| **`FJInterpreter`**            |                         |                               |
| `eval'`                        | :white_check_mark:      | :negative_squared_cross_mark: |
| `eval`                         | :white_check_mark:      | :negative_squared_cross_mark: |
| `subst`                        | :white_check_mark:      | :negative_squared_cross_mark: |

Les tests qui ont pu être réalisés se résument à s'assurer que les objets créés par l'instanciation de la classe `C` (correspondant au *type definition*). Ces instanciations ont permis des tester l'ensemble des classes du package `src.Parser.DefintionP`.

## Quelles méthodes en Haskell, reste-il à convertir en Java ?

La partie V2 n'a pas pu être implémentée vers le Java, faute de temps.

|                           |                      |                        |
| ------------------------- | -------------------- | ---------------------- |
| **`FJParser`**            |                      |                        |
| `Constr`                  | `Sign`               |                        |
| `FJUtils`                 |                      |                        |
| `absmethods`              | `mtype`              | `lambdaMark`           |
| `removeRuntimeAnnotation` |                      |                        |
| **`FJTypeChecker`**       |                      |                        |
| `throwError`              | `typeof`             | `methodTyping`         |
| `classTyping`             | `interfaceTyping`    | `ctTyping`             |
| **`FJGenerator`**         |                      |                        |
| `maybeElements`           | `pickVar`            | `genClassName`         |
| `genInstantiableType`     | `genClassType`       | `genInterfaceTypeList` |
| `genVar`                  | `genAttrs`           | `genMethod`            |
| `genSign`                 | `genMethods`         | `genClass`             |
| `genInterface`            | `addClass`           | `addInterface`         |
| `addType`                 | `genClassTable`      | `genCreateObject`      |
| `genFieldAccess`          | `genMethodInvk`      | `genCast`              |
| `genClosure`              | `ccreateobject`      | `cfieldaccess`         |
| `cmethodinvk`             | `cucast`             | `cdcast`               |
| `cscast`                  | `cclosure`           | `genExpr`              |
| `genExpression`           | `instance Arbitrary` |                        |

# Discussion

## Quels sont les points forts et faibles de notre interprète en Java

### Points forts

#### Patron *Singleton* pour la $CT$

Il n'est possible d'instancier qu'une seule fois la classe `CT` (*class table* $CT$) car cette classe a été implémentée en suivant le patron *Singleton*.
En effet, la *class table* sert de "dictionnaire" en recensant toutes les classes et méthodes du programme évalué, et est donc universel au programme et doit, pour des soucis d'homogénéité être unique.

#### *TDeclaration* et *T*

Les types correspondants à des Interfaces (`src.Parser.DefinitionP.I`) ou des Classes (`src.Parser.DefinitionP.C`) étendent tous les deux le type $T$, ce qui permet de prévoir une factorisation de code. Par exemple avec le mot clé `extends` commun aux interfaces et aux classes.
Il est prévu également une autre factorisation de code pour la déclaration du corps des classes et des interfaces qui comportent toutes deux des méthodes.

#### `Set` représentant les séquences

Pour réprésenter des séquences (ex. $\overline{T}$), nous avons utilisés des `TreeSet` pour se protéger des duplications des données, tout en gardant une structure de données itérable.
Cela est particulièrement utile pour les séquences d'interfaces ($\overline{I}$) qui définissent les interfaces implémentées ou étendues. En effet, la duplication de nom d'interfaces dans une même liste d'implémentation en Java n'est pas autorisée. De plus les auteurs utilisant le terme de *sequence* font selon nous référence à la notion d'ensemble qui selon nous est constitué d'éléments uniques.
Nous donnons plus de détails sur ce choix dans la partie *Choix réalisés*.

### Points faibles

Seule l'approche V1 a pu être explorée et traduite dans notre projet, faute de temps.

Par ailleurs, nous n'avons pas pu traduire l'ensemble des méthodes de la V1 vers le Java.

De plus, certaines des méthodes qui ont pu être implémentées n'ont pas pu être toutes testées et optimisées (i.e temps d'exécution et de ressources consommées (nombre d'appels entre les objets, redondances des boucles...)).

## Choix réalisés

Comme le montre les points suivants, plusieurs choix on pu être réalisés lors de l'implémentation des interpréteurs.

- Emballer variables de type String dans des classes (ex. `TypeName` dans la classe `Type`).
- Utilisation de structures de représentation des séquences (ex. `Set` à la place de `List` ou `Array`).

Dans la partie suivante nous discuterons de ce dernier choix.

### Structures de représentation des séquences

Dans l'article les auteurs utilisent des séquences ($\overline{T} \overline{I} \overline{M}$)
Dans l'implémentation, ces séquences ont été stockées dans des structures de données. Dans chaque cas, nous avons d'abord vérifié si l'objet lié allait être modifié ou non, et si nous avions besoins de tableaux, de listes ou de collections.
Ce point du rapport va discuter notre choix entre l'utilisation du type `List` ou du type `Set`.

#### options 1 : utilisation du type `List`

##### Avantages

- limite la possibilité d'un `ArrayOutOfBound`
- facilite le parcours des données
- maintient l'ordre d'insertion

##### Inconvénients

- pas de comparaisons entre les données
- pas de protection contre les doublons (il aurait pu être possible de le faire manuellement en redéfinissant des `equals()` à chaque type d'`Expr`, mais cela aurait été fastidieux et source d'erreurs potentielles)

#### option 2 : utilisation du type `Set`

##### Avantages

- protection contre les doublons
- le type `Set` n'autorise qu'une seule valeur nulle dans la collection. Comme le code (cf. `FJUtils` et `FJInterpreter`) contient déjà des contrôles pour éviter qu'un null soit inséré dans une collection, cette caractéristique de structure donnée était adéquate à notre utilisation

##### Inconvénients

- moins adapté pour les parcours que le type `List`
- nécessite un temps de travail supplémentaire pour la définition de l'implémentation de l'interface `Comparable`

#### Conlusion

Notre première version du projet utilisait des **tableaux** pour les structures de données (ex. en attribut de la classe `Signature.java`, on avait `Field[] params`).
Ce choix avait été pris pour les cas où nous étions sûrs que les données étaient définies, et donc que la taille du tableau serait fixe.

La seconde version du projet utilisait des **listes** (`List` puis `ArrayList`).
L'objectif principal était de limiter la possiblité d'un `ArrayOutOfBound` (dépassement de la taille du tableau quand on le parcourt ou quand on y ajoute un nouvel élément). Ensuite, les listes permettaient une navigation plus simple dans les parcours, comme dans les boucles `for`, avec plus de flexibilité que les tableaux.
Une première implémentation était l'utilisation du type `List<>` dans les signatures des méthodes, puis dans l'implémentation un `ArrayList<>`. Cela offre un code plus sûr et plus adapté aux pratiques professionnelles. Cependant, pour simplifier temporairement le code, nous sommes passés à utiliser `ArrayList<>` à chaque occurence.
Nous avions le choix entre `ArrayList` et `LinkedList` :

- `ArrayList` reproduit une structure sous forme de tableau (basée sur l'index d'un élément) et permet de faciliter les get(), ce qui nous semblait le plus pertinent à cette phase du projet.
- `LinkedList` lie les objets de la liste entre eux, ce qui fait que pour récupérer un élément précis, il faut parcourir chaque élément de la liste pour le trouver. Nous avons conclu que l'`ArrayList` était plus pertinent pour optimiser les accès aux éléments de la liste.

La troisième et actuelle version utilise des **sets** (`TreeSet`).
L'objectif était de protéger nos séquences en évitant d'y insérer des doublons.

- Nous avons fait le choix du format `TreeSet` pour garder en mémoire l'ordre d'insertion des objets, et pouvoir utiliser des index sur la structure de données. Ce cas à notamment était nécessaire lors de l'implémentation de la méthode `eval'` dans le cas des `FieldAcess`, où on utilise `Data.List.findIndex` pour récupérer l'index d'un élément, puis retourner l'élément lié à cet index.

# Rétrospectives

Dans le cas où nous devrions refaire ce projet d'initiation à la recherche, nous aurions demandé quelle était la priorité entre finir la compréhension de l'article ou bien l'implémentation des interprètes en Java.

Dans notre cas, nous avons accordé beaucoup de temps lors de nos premières séances à la lecture de l'article, ce qui nous a empêché de nous intéresser au code en Haskell et de commencer à coder, ce qui pourrait expliquer une v1 non terminé.

Si nous avions à recommencer, nous aurions une courbe d'apprentissage sur la compréhension d'Haskell moins longue.

# Annexes

## [Fichier de notes](https://pad.faire-ecole.org/s/QrN2T4Tq_#)

### Lexique

#### concepts java

- `functional interface` : interface possédant une et une seule méthode abstraite
- `abstract method`: une méthode sans implémentation
- `default method` : une méthode déclarées dans une interface
- $\lambda$`-expression` / $\lambda$`-fonction`: fonction anonyme qui implémente une interface fonctionnelle mais qui est écrit sans type. Une $\lambda$`-expression` ne peut cependant être appelée sans type.
- `Object`: Base class of every class, which has no fields (so the invocations of super have no arguments) and no methods
- `target type` : type d'une $\lambda$`-expression` inféré par le compilateur en fonction du contexte de celle-ci. Ce type est nécessaire pour que la $\lambda$`-expression` puisse être invoquée.

#### concepts logiques

- séquent : conjonction d'hypothèses $\vdash$ disjonction de conclusion
- règles : $\frac{premises}{conclusion}$ de séquent

#### concepts mathématiques

- $\overline{x}$ : liste/séquence possiblement vide de $x$
- $\bullet$ : séquence vide : `[]`
- $\langle \rangle$ : séquence dont l'ordre est à prendre en compte (à la différence d'une liste)
- $\Gamma$ : représente un mapping fini : $\overline{x}:\overline{T}$, reliant les variables $x$ à leur type $T$ => **contexte**/**environnement**
- $CT$ : représente une table qui associe le noms des interfaces ou des classes à leur déclaration ($L$ ou $P$)

#### méthodes

- `mtype` : permet d'obtenir le type d'une méthode m dans une classe C, en renvoiyant une paire de `[liste de B, B]` <=> `liste de paramètre et type`
- `mbody` : permet d'obtenir une paire de `[liste de B, B]` <=> `liste des paramètres, expression`
- $\lambda$`mark` : fonction qui ajoute une définition de `cast` si et seulement si la $\lambda$`-expression` apparaît dans le code source

### Interprétations du code Haskell

## [Fichier de log](https://pad.faire-ecole.org/s/suGTsvbBS#)

Remarque : certaines dates ne sont pas liées à des séances prévues dans le planning de la formation, et sont du travail en autonomie pour avancer le plus possible sur le projet. Elles n'ont donc pas de "Prévu" et sont indiquées par "Réalisé (avancement en autonomie)".

### 14 10 2022

#### Prévu

- Lecture de l'introduction pour l'écriture d'un plan,
- Lecture du reste de l'article pour associer des mots-clés dans chacune des parties de notre plan,
- Réalisation d'une première version d'un schéma récapitulatif de l'article.

#### Réalisé

- Article
  - Partie 1, 2, 3 (intro)

### 28 10 2022

#### Prévu

- Avancée de la lecture de l'article
- Identification des séquent et des règles associées dans le code Haskell

#### Réalisé

- Internet
  - Vocabulaire avec les sécents
    - <https://www.everything2.org/index.pl?node=Judgment>
    - <https://fr.wikipedia.org/wiki/Calcul_des_s%C3%A9quents>
  - Class Table
    - <https://en.wikipedia.org/wiki/Map_(mathematics>)
  - [Type soundness](https://cs.stackexchange.com/questions/82155/is-there-a-difference-between-type-safety-and-type-soundness)
- Article
  - Partie 3.1 et 3.2 (sauf Fig.7)

### 04 11 2022

#### Prévu

- Revoir la notion de `default methods` en java
- essayer une première implémentation en java des classes et interfaces en suivant la première approche de l'article

#### Réalisé

- travail initié sur la méthode `eval()`
- première version des objets de type `Expr`

#### haskell

##### mots clés

`let in` = créer une variable et son contenu qui va être utile dans le `in` - [ref](https://lyah.haskell.fr/syntaxe-des-fonctions#:~:text=clause%20where.-,Let%20it%20be,-Les%20liaisons%20let)

`maybe` = soit possède une valeur (Just x, x étant une valeur d'un type quelconque) soit n'a pas de valeur (Nothing) - [ref](https://stackoverflow.com/questions/29456824/what-is-the-maybe-type-and-how-does-it-work)

`case of` = système de motif pour écrire les différentes valeurs possibles - [ref](https://lyah.haskell.fr/syntaxe-des-fonctions#:~:text=est%20plus%20lisible.-,Expressions%20case,-Beaucoup%20de%20langages)

```haskell
head' :: [a] -> a
head' [] = error "No head for empty lists!"
head' (x:_) = x

head' :: [a] -> a
head' xs = case xs of [] -> error "No head for empty lists!"
                      (x:_) -> x
```

#### Travaux sur les expressions

From Haskell to Java

- Haskell : [FJParser.hs](https://github.com/fjpub/fj-lam/blob/master/FJParser.hs#L34)
- Java : [Commit](https://github.com/Naedri/java-interpreter/commit/c76a92e2652d3fa92ea8cb8ddf150bfd2c126338)

### 07 11 2022

#### Réalisé (avancement en autonomie)

- Article
  - Travail sur les règles de typage de la figure 5
- Code :
  - [commit](https://github.com/Naedri/java-interpreter/commit/f89e1df42ad6513dc70fe9fbd4784c029de99adc) : Class`Definition.ClassTable` et interface `IUtils.java`

### 10 11 202

#### Réalisé (avancement en autonomie)

- Article :
  - Lecture appronfondie de `substyping`

```haskell=10
-- Function: subtyping
-- Objective: Check classes for subtyping.
-- Params: Class table, Class A (ou interface A), Class B (ou interface B).
-- Returns: Returns if class A is subtype of class B.
-----------------------------------------------------
subtyping :: CT -> String -> String -> Bool
subtyping ct t t' 
-- class A <=> t
-- class B <=> t'
  | t == t' = True -- comparaison du hash code (ne pas déterminer avec typeof, car on ne sait pas s'il renvoie le T le plus bas ou non => à déterminer dans intellij 
  | otherwise = 
      case (Data.Map.lookup t ct) of 
        -- renvoie un T ( => ligne 10 de FJParser.hs) => on va après le décomposer 
        -- on récupère t dans la ct : ct.get(t) ; est ce que t est un C ou un I ?
        Just (TClass (Class _ t'' il _ _ _)) ->
          -- Décomposition : Just (<datatype> (<constructeur> <params>)) => ligne 17 de FJParser.hs
          -- (T) t : C, avec TClass <=> nom de data type / Class <=> le nom du constructeur / _ / t'' <=> super type de la classe t càd classe A / il <=> liste des interfaces de t (classe A)
          if (t' == t'' || Data.List.elem t' il) then
            -- Data.List.elem t' il <=> regarde si t' est un élement de il <=> il se pose la question : est ce que la classe B est parmis les interfaces que A implémente <=> est ce que A implémente B
            True
          else 
            subtyping ct t'' t' || -- est ce que la superclasse de A est sous type de B
            Data.List.any (\t'' -> subtyping ct t'' t') il -- il.forEach(i -> {subtyping(ct)}) <=> est ce que parmis les interfaces/classes de A et ses sur-type, il y en a une qui est un sous-type de la classe B 
        -- traduction proposée : "est ce que parmis les interfaces implémentées par A, une est sous-type de B"
        Just (TInterface (Interface _ il _ _)) ->
          -- Décomposition : Just (<datatype> (<constructeur> <params>)) => ligne 23 de FJParser.hs
          -- t : I, avec t non classe A mais interface A
          Data.List.elem t' il || -- est ce que la classe B se trouve parmi les interfaces de A
          Data.List.any (\t'' -> subtyping ct t'' t') il -- si parmi les interface de A ou de leur sur-type
        _ -> False -- si c'est ni une classe ni une interface 
```

- Code :
  - [commit](https://github.com/Naedri/java-interpreter/commit/ec6f1519f732f767582357595ba7d7ac96ddc059) : Class `Expression.Expr`

### 14 11 2022

#### Réalisé (avancement en autonomie)

- Code :
  - [commit](https://github.com/Naedri/java-interpreter/tree/cd4366b717e94213910779c2afacdf1f4396a135) : Class `Definition. L P K M S`

### 15 11 2022

#### Réalisé (avancement en autonomie)

- Code :
  - [commit](https://github.com/Naedri/java-interpreter/tree/fe7dbbee5b5f77abe86be215b6b07168a2d60b6b) : Class `TypeError`
  - [commit](https://github.com/Naedri/java-interpreter/tree/eeda27f77c360ca80fa11182a83f75499ab6fd38) : Class `Expression`

### 16 11 2022

#### Prévu

- focus sur le fichier FJUtils.hs (permet de faire le fichier FJInterpreter.hs)
- lecture + traduction de FJUtils.hs

#### Réalisé

- Article :
  - Lecture approfondie de `fields`

```haskell=40
-- Function: fields
-- Objective: Search for a class on class table and returns its fields.
-- Params: Class table, Class name.
-- Returns: A monad Maybe containing the field list or Nothing.
-----------------------------------------------------------------------
fields :: CT -> String -> Maybe [(Type,String)]
fields _ "Object" = Just [] -- si le nom de la classe est 'object' on renvoie une table vide car celle-ci ne contient pas d'attributs 
fields ct c = case (Data.Map.lookup c ct) of -- on cherche dans la classeTable le nom de la classe
              --  si on retrouve la classe
                Just (TClass (Class _ c'' _ attrs _ _)) -> -- on décompose la classe retrouvée pour en extraire le nom de sa superclasse (c'') et les fields/attributs de la classe c (attrs)
                  case (fields ct c'') of -- dans le cas on l'on peut faire un appel récursif à fonction fields, on le fait
                    -- et si on récupère les attributs de la superclasse
                    Just base -> Just (base ++ attrs) -- on ajoute les nouveaux attributs (ceux de base - de la superclasse) (base) aux précédents (ceux des classes inférieures) (attrs)
                    -- et si on ne trouve rien
                    _ -> Nothing -- on ajoute rien de plus
                -- si on ne retrouve pas la classe
                _ -> Nothing -- on ne renvoie rien
```

- Lecture approfondie de `absmethods`

```haskell=58
-- Function: absmethods
-- Objective: Search for a class or interface on class table and returns its
-- abstract methods.
-- Params: Class table, Class name.
-- Returns: A monad Maybe containing the method signature or Nothin.
----------------------------------------------------------------------------
absmethods :: CT -> String -> Maybe [Sign]
absmethods _ "Object" = Just [] -- si le nom de la classe est 'object' on renvoie une table vide car celle-ci ne contient pas de méthode 
absmethods ct t = 
  case (Data.Map.lookup t ct) of -- on cherche dans la classeTable (ct) le nom de la classe/interface(t)
    -- si on retrouve le nom donné en tant que classe dans la CT
    Just (TClass (Class _ t' il _ _ _)) -> -- on décompose la classe retrouvée pour en extraire le nom de sa superclasse (t') et la liste des interfaces que cette classe implémente (il <=> implemented list)
      -- => ici (t) est une classe
      case (absmethods ct t') of  -- dans le cas on l'on peut faire un appel récursif à fonction absmethods, on le fait avec la classeTable (ct) et la superclasse (t')
        Just bam -> -- si on obtient qlq chose (càd une liste de signature de la superclasse <=> base abstract method <=> les méthodes non implémentées par la superclasse)
          -- on ajoute dans une liste (bam') les méthodes abstraites de toutes les interfaces implémentées par la classe (il)
          -- Data.List.concatMap permet d'obtenir à partir d'une liste des liste (2dimensions), une liste de méthodes (1dimension)
          let bam' = Data.List.concatMap (\i -> case (absmethods ct i) of
                                                  Just am -> am
                                                  _ -> []
                                         ) il
              -- on construit une nouvelle liste (bam'') qui contient la liste des méthodes abstraites (les signatures) sans doublons de bam et bam'' 
                -- bam signatures de la superclasse (t) <=> liste != bam' <=> signatures de l'ensemble des interfaces qu'implémente (t)
              -- unionBy est similaire à union, mais il permet de fournir un test d'égalité qui lorsqu'il est True n'ajoute pas les élements du membre de droite => on fait une left union
              bam'' = unionBy (\(Sign _ n' _) 
                                (Sign _ n'' _) -> n' == n'') bam bam'
              -- on récupère la liste des signatures methodes concrètes de la classe (t) et de la superclasse (t') (cam <=> concrete abstract method) 
              cam = case (methods ct t) of
                      Just meths -> 
                        case (methods ct t') of
                          Just bmeths -> 
                            unionBy (\(Sign _ n' _) 
                                      (Sign _ n'' _) -> n' == n'')
                              (Data.List.map (\(Method s e) -> s) meths)
                              (Data.List.map (\(Method s e) -> s) bmeths)
                          _ -> Data.List.map (\(Method s e) -> s) meths
                      _ -> []
            in Just (bam'' Data.List.\\ cam) -- on retire, de la liste des signatures des interfaces et de la superclasse de la classe (t) (bam''), les signatures des méthodes des concrètes de la classe (c) (avec '\\' <=> intersection)
        _ -> Nothing -- dans le cas on l'on ne peut pas faire d'appel récursif, ou que la liste des signatures retournée est vide, on n'ajoute rien
    -- si on retrouve le nom donné en tant que interface dans la CT
    Just (TInterface (Interface _ il ameths _)) -> -- on décompose l'interface retrouvée pour en extraire la liste des interface qu'elle étend (il) et la liste des signatures de méthode qu'elle contient (ameths <=> abstract methods)
    -- => ici (t) est une interface
      -- on ajoute dans une liste (bam) les méthodes abstraites de toutes les interfaces implémentées par l'interface (il)
      let bam = Data.List.concatMap (\i -> case (absmethods ct i) of
                                             Just am -> am
                                             _ -> []
                                    ) il
          -- on forme une liste (ameths') sans doublons qui contient les signatures de méthode de l'interface (ameths) et les signatures de méthodes des 'super' interfaces (bam) 
          ameths' = unionBy (\(Sign _ n' _) 
                              (Sign _ n'' _) -> n' == n'') ameths bam
        in Just (ameths')
    _ -> Nothing

```

- Code :
  - [commit](https://github.com/Naedri/java-interpreter/tree/fc09dcf1c2d925a39acc8de5d8ab2154d72039b2): Ajout de constructeur simplifié pour les classes Definition
  - [commit](https://github.com/Naedri/java-interpreter/tree/ff4d22d103380f42c4a9676a545c24941d82fc80) : Ajout d'un patern singleton pour la classe Definition et d'un attribut statique pour CT et Environnement
  - [commit](https://github.com/Naedri/java-interpreter/tree/3b1ecdcfc2d474a22d626f88c0e864ac0abaadd9) : Travaux en cours sur la fonction subtyping

### 18 11 2022

#### Prévu

- lecture approfondie du reste des méthodes de `FJUtils.hs`
- implémentation en Java de
  - `FJUtils.subtyping()` et `FJUtils.fields()`
  - (`FJUtils.absmethods()` selon temps disponible et avancement)

#### Réalisé

- Article
  - Lecture approfondie de `absmethods` (suite)
  - Lecture approfondie de `methods`

```haskell=112
-- Function: methods
-- Objective: Search for a class on class table and returns its methods.
-- Params: Class table, Class name.
-- Returns: A monad Maybe containing the method list of Nothing.
------------------------------------------------------------------------
methods :: CT -> String -> Maybe [Method]
methods _ "Object" = Just [] -- si le nom de la classe est 'object' on renvoie une table vide car celle-ci ne contient pas de méthode 
methods ct t = 
  case (Data.Map.lookup t ct) of 
    -- on retourne sa définition si on la retrouve dans la ct
    Just (TClass (Class _ cb il _ _ meths)) -> -- on récupère la superclasse (cb), la liste des interfaces (il) et les méthodes concrètes (meths)
    -- => ici (t) est une classe
      case (methods ct cb) of
        Just bms -> -- les méthodes concrètes de la superclasse (bms <=> base concrete methods)
          -- concaténation dans une seule map (bim <=> base interface methods) les defaults méthodes de l'ensemble des interfaces (il)
          let bim = Data.List.concatMap (\i -> case (methods ct i) of
                                                 Just m -> m
                                                 _ -> []
                                        ) il
              -- left union des méthodes concrètes de la classe (t) avec les méthodes de la superclasse (bms)
              m' = unionBy (\(Method (Sign _ n' _) _)
                             (Method (Sign _ n'' _) _) -> n' == n'') meths bms
              -- left union des méthodes concrètes de la classe et la superclasse (m') avec les méthodes default des interfaces (bim)
              m'' = unionBy (\(Method (Sign _ n' _) _)
                              (Method (Sign _ n'' _) _) -> n' == n'') m' bim
            in Just m''
        _ -> Nothing
    Just (TInterface (Interface _ il _ defmeths)) -> -- on récupère la liste des interfaces (il) etendues par l'interface (t) et ses default methodes (defmeths)
    -- => ici (t) est une interface
      -- concaténation dans une seule map (bim <=> base interface methods) les defaults méthodes de l'ensemble des interfaces que l'interface (t) implémente (il)
      let bim = Data.List.concatMap (\i -> case (methods ct i) of 
                                             Just m -> m
                                             _ -> []
                                    ) il
          -- left union des defaults méthodes (defmeths) de l'interface (t) et de celles des 'super' interfaces (bim)  
          m' = unionBy (\(Method (Sign _ n' _) _)
                         (Method (Sign _ n'' _) _) -> n' == n'') defmeths bim
        in Just m'
    -- on retourne rien si on la ne retrouve pas dans la ct
    _ -> Nothing
```

- Lecture approfondie de `mtype` (avec des difficultés pour le cas du cas avec `_ -> Nothing` - ligne 178 ci dessous)

```haskell=154
-- Function: mtype
-- Objective: Search for a class on class table, then looks up for a method 
-- and returns its type.
-- Params: Class table, Method name, Class name.
-- Returns: A monad Maybe containing the method type.
---------------------------------------------------------------------------
mtype :: CT -> String -> String -> Maybe ([Type], Type)
mtype _ _ "Object" = Nothing
mtype ct m t = 
  case (absmethods ct t) of
    Just absmeths ->  -- si on a : Just [x] ou Just[x,x...] /!\ mais aussi Just [] (<=> cas pour une classe concrète) 
      case (Data.List.find (\(Sign _ m' _) -> m == m') absmeths) of -- dans les méthodes abstraites récupérées, on sélectione la méthode (m), en ne prennant que sa signature
        Just (Sign r _ p) -> Just (fst (unzip p), r) -- on récupère seulement le type des arguments de la méthode (m)
        -- avec unzip qui permet de prendre les types de tous les paramètres de méthodes (=> [(a,b)] --unzip-> ([a],[b]))   
        _ -> case (methods ct t) of -- si la classe est concrète
               Just meths -> 
                 case (Data.List.find 
                         (\(Method (Sign _ m' _) _) -> m == m') meths) of --- dans les méthodes abstraites récupérées, on sélectione la méthode (m), en ne prennant que sa signature
                   Just (Method (Sign r _ p) _) -> Just (fst (unzip p), r) -- même chose que précédement
                   _ -> Nothing
               _ -> Nothing
    -- /!\ la phrase suivante est fausse : si (t) présente n'a pas au moins une méthode abstraite (càd que (t) est une classe concrète)
    -- /!\ la phrase suivante est vrai : si la classe n'est pas trouvée dans la ct (car c'est le seul cas où Nothing est renvoyé par absmethods) 
    -- Les auteurs auraient dû mettre : Nothing -> Nothing
    _ -> Nothing -- => on ne renvoie rien
```

- Code
  - [commit](https://github.com/Naedri/java-interpreter/tree/0654821827e91c7c42917d3c60afdf74fca9e105): method FJUtils.subtyping()
  - [commit](https://github.com/Naedri/java-interpreter/tree/fb4ebdbfd53cf6939cfa77f9b89c4cf0ceb9a21d) : method FJUtils.fields()
  - [commit](https://github.com/Naedri/java-interpreter/tree/5cc8dbf5e15a3f1ac879f2c539ae3d115259a203) : completions des deux précédents commits sur subtyping et fields
  - Difficultés sur la manière d'implémenter fields et subtyping de manière récursive

### 19 11 2022

#### Réalisé (avancement en autonomie)

- Article
  - Lecture approfondie de `isValue`

```haskell=202
-- Function: isValue 
-- Objective: Check if an expression represents a value.
-- Params: Class table, Expression.
-- Returns: Boolean indicating if an expression is a value.
-----------------------------------------------------------
isValue :: CT -> Expr -> Bool
-- si l'Expr fournie est de type CreateObject (-- Object Instantiation)
isValue _ (CreateObject c []) = True -- si l'on instancie un objet sans argument, pas la peine de regarder la validité des paramètres
isValue ct (CreateObject c p) = Data.List.all (isValue ct) p  -- si l'on instancie un objet avec argument, on appelle isValue sur chacun s'ils bien renvoient une valeur
-- si l'Expr fournie est de type Closure
isValue ct (Closure _ _) = True  -- <=> isValue _ (Closure _ _) = True -- ct aurait pu être écrit avec '_'
-- si l'Expr fournie est de type Cast d'une Closure
isValue ct (Cast _ (Closure _ _)) = True -- isValue _ (Cast _ (Closure _ _)) = True
-- pour les autres cas de Expr (càd Var, FieldAccess et MethodInvk)
-- en effet, un appel a une methode n’est pas une valeur mais doit etre execute/reduit pour obtenir une valeur, comme une variable qui sera subsitituee par une expression
isValue _ _ = False
```

- Code :
  - [commit](https://github.com/Naedri/java-interpreter/tree/a0a281d6ebae3e611ab272c02a76f2d564afc1cf) : isValue
  - [commit](https://github.com/Naedri/java-interpreter/tree/f65e41bea74e4afcc2f80df27e3b3cd3b09fdda7) : eval' [in_work]

### 20/11/2022

#### Réalisé (avancement en autonomie)

- Article :
  - Lecture approfondie de `lambdaMark`

```haskell=219
-- Function: lambdaMark
-- Objective: Annotate the types for lambda expressions.
-- Params: Expression, Type.
-- Returns: A lambda expression annotated with its type, or the expression if
-- it is not a lambda expression.
-----------------------------------------------------------------------------
lambdaMark :: Expr -> Type -> Expr
lambdaMark c@(Closure _ _) (Type t) = Cast t c -- c alias/comme une Closure
lambdaMark e _ = e
```

- Code
  - [commit](https://github.com/Naedri/java-interpreter/commit/9c03475db7bacf516c83c6f45789f4f613d8035b): génération du diagramme de classe
- Code
  - [commit](https://github.com/Naedri/java-interpreter/tree/3e087ce95ae7ea29917e9317643a82f1d5ff384f): test pairobject

### 23/11/2022

#### Réalisé (avancement en autonomie)

- Code
  - [commit](https://github.com/Naedri/java-interpreter/tree/374b193dacef963ca900653c1b1f05b644b7047a) : TreeSet pour les attributs + Comparator pour les classes étendues

### 24 11 2022

#### Prévu

- implémentation de la méthode `FJIntepreter.eval'()`
  - MethodInvk
  - Cast
  - Closure
- correction de l'erreur `StackOverFlow` apparue dans l'exécution du main lors de l'instaciation d'une classe

#### Réalisé

- Code :
  - [commit](https://github.com/Naedri/java-interpreter/commit/431d1a77a9d34aa395dd1114e1384eddaa220f20) : refactor transferant les sousclasses en classes
  - [commit](https://github.com/Naedri/java-interpreter/commit/53c9c8ac5e29104269f53f12d3278e30990f6bac) : Fix de la compilation du main (`stackoverflow`) en évitant les appels récursifs à CT et C et en créant une variable statique `baseObject`
  - [commit](https://github.com/Naedri/java-interpreter/commit/6f6000f8e141f1c4dcf2ca5671073e6291ec00f0): utilisation de Set au lieu de liste
  - [commit](https://github.com/Naedri/java-interpreter/tree/c3f4238f2f1bfaa17a5d73d0f7e077afd999070f): mbody
  - [commit](https://github.com/Naedri/java-interpreter/commit/6789bb27ecd046fee5a3e1f1470baa9502a75304): refactor de différentes méthodes de FJUtils
  - [commit](https://github.com/Naedri/java-interpreter/tree/e858c3d87bbbc9b05349913ea8783748c8acbd15): avancé de evalPrime

### 25 11 2022

#### Prévu

- finir l'implementation de `FJInterpreter.subst()`
  - refactoring général du code
- rédaction du rapport

#### Réalisé

- Code :
  - [commit](https://github.com/Naedri/java-interpreter/tree/7fc79ed8ae7a98341e1b0a35c600ae2d5c1545dd) : `evalPrime` et `eval`
  - [commit](https://github.com/Naedri/java-interpreter/tree/61c0b044481641f8990329aa2e59e9858b82e2c8) : reste des fonctions `FJInterpreter` : `subst`
  - [commit](https://github.com/Naedri/java-interpreter/tree/48579aa5ca2fd8723551fa8f890e983ac904bc94) : Refactoring général
- Rédaction du rapport
