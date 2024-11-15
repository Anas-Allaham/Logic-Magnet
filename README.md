# Logic Magnet

### Developed by: محمد أنس اللحام
### Class: فئة ثالثة
### Framework: Java

---

## Overview
Logic Magnet is a puzzle game where you can choose to play interactively or let the PC solve it for you using:
- **Breadth-First Search (BFS) algorithm**
- **Depth-First Search (DFS) algorithm**
- **Uniform-Cost Search (UCS) / Dijkstra**

---

## How to Play

1. **Run the Project**: Start the project in your IDE.

2. **Choose a Level**: Enter a level number in the range [1, 25] in the console.

3. **Game Modes**:
    - **Play Manually**:
        - A GUI will appear where you can play the game.
        - To make a move:
            - Click on the magnet you want to move.
            - Click on the target cell where you want to place the selected magnet.
        - After completing the level, return to the IDE console to enter any query number displayed.

    - **Let the PC Solve It**:
        - Enter `6` in the console to let the PC solve the puzzle automatically using the BFS algorithm.
        - Note that this may take some time depending on the complexity of the level.

---

## Features

- **Interactive Gameplay**: Play the puzzle game with an intuitive GUI.
- **Automated Solver**: Watch the PC solve the puzzle with any algorithm you want.

---

##توصيف اللعبة (Game details)

### 1. فضاء الحالات (State Space)
**تمثيل الحالة**: يتم تمثيل الحالة كشبكة من قطع مختلفة الألوان والوظائف، تتضمن القطع الحديدية ('m' رمادي)، والمغناطيسية ('b' أزرق و'r' أحمر)، والخانات الفارغة 'e'، وخانات الهدف.
**محتويات الحالة**: تتضمن معلومات عن أنواع الخانات (فارغة، مغناطيسية، حديدية).

### 2. الحالة الابتدائية (Initial State)
يُدخل مصفوفة ثنائية تعبر عن كل قطعة بأحد الرموز السابقة، إضافةً إلى مواقع خانات الهدف التي يجب تغطيتها للفوز.

### 3. العمليات (Actions)
- **تحريك القطع المغناطيسية**:
    - يمكن تحريك القطعة البنفسجية لأي خانة فارغة؛ عند تحريكها تتنافر معها القطع الحديدية المجاورة في الصف أو العمود.
    - يمكن تحريك القطعة الحمراء لأي خانة فارغة؛ عند تحريكها تتجاذب معها القطع الحديدية المجاورة.
- **القيود**: القطع الحديدية لا تتحرك مباشرة، بل فقط عند تأثرها بالقطع المغناطيسية.

### 4. الحالات النهائية (Goal States)
تكون الحالة النهائية عند تغطية جميع خانات الهدف بقطع حديدية أو مغناطيسية، بما يتوافق مع قواعد اللعبة.

الحل النهائي هو سلسلة من التحركات التي تؤدي لتحقيق الحالة النهائية.

Enjoy playing Logic Magnet!
