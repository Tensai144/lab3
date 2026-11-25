package lab3;

public class GitPoem {
    // Создание полей класса
    private String author;
    private String name;
    private int savesLimit;
    private String[] poem;
    private String[][] saves;
    private int savesCount;

    // Конструктор с параметрами: Название, Автор, Лимит сохранений
    public GitPoem(String name, String author, int savesLimit) {
        this.name = name;
        this.author = author;
        this.savesLimit = savesLimit;
        // Изначально строк в стихотворении и самих сохранений нет
        poem = new String[0];
        savesCount = 0;
        saves = new String[savesLimit][0];
    }

    // Переопределение toString -  вывод стихотворения
    @Override
    public String toString() {
        String result = "«" + name +  " - " + author + "»";

        for (int i = 0; i < poem.length; i++)
            result = result + "\n" + poem[i];

        return result + "\n\n";
    }

    // Добавление строки в конец стиха
    public void addString(String s) {
        // Создаем новый массив строк, добавляем в него еще одно место для новой строки
        String[] poemUpdated = new String[poem.length + 1];

        // Перезаписываем все предыдущие строки в новый массив
        for (int i = 0; i < poem.length; i++)
            poemUpdated[i] = poem[i];

        // Добавляем новую строчку
        poemUpdated[poemUpdated.length - 1] = s;

        // Перезаписываем наш массив строк (стих)
        poem = poemUpdated;
    }

    // Удаление строки по номеру
    public void deleteString(int numString) {
        numString--;
        // Создаем массив на одно место меньше
        String[] poemUpdated = new String[poem.length - 1];

        // Перезаписываем в него все строки до той, которую нужно удалить
        for (int i = 0; i < numString; i++)
            poemUpdated[i] = poem[i];

        // Сдвигаем все оставшиеся строки на 1 позицию влево, относительно старого массива
        for (int i = numString + 1; i < poem.length; i++)
            poemUpdated[i - 1] = poem[i];

        // Перезаписываем наш массив строк (стих)
        poem = poemUpdated;
    }

    // Вставка строки по номеру
    public void insertString(String s, int numString) {
        numString--;
        // Создаем новый массив строк, добавляем в него еще одно место для новой строки
        String[] poemUpdated = new String[poem.length + 1];

        // Перезаписываем в него все строки до той, которую нужно вставить
        for (int i = 0; i < numString; i++)
            poemUpdated[i] = poem[i];

        // Вставляем новую строчку
        poemUpdated[numString] = s;

        // Сдвигаем все оставшиеся строки на 1 позицию вправо, относительно старого массива
        for (int i = numString + 1; i < poemUpdated.length; i++)
            poemUpdated[i] = poem[i - 1];

        // Перезаписываем наш массив строк (стих)
        poem = poemUpdated;
    }

    // Сохранение версии
    public void save() {
        // Создаем копию стиха
        String[] currSave = new String[poem.length];

        for (int i = 0; i < poem.length; i++)
            currSave[i] = poem[i];

        // Если мы уже сохранили максимальное кол-во версий, то самую старую удалим
        if (savesCount == savesLimit)
            savesCount--;

        // Сдвигаем все предыдущие сохранения на 1 позицию вправо, при этом самое старое сохранение
        // автоматически удалится (если мы уже достигли лимита сохранений)
        for (int i = savesCount; i > 0; i--) {
            saves[i] = saves[i - 1];
        }

        // Записываем новое сохранение
        saves[0] = currSave;

        // Увеличиваем счётчик сохранений
        savesCount++;
    }

    // Вывод сохранения
    public void printSave(int numSaved) {
        // Текущая версия
        if (numSaved == 0) {
            System.out.println(this);
            return;
        }
        if (numSaved > savesCount) {
            System.out.println("Такого сохранения нет");
            return;
        }

        // Сохранение
        numSaved--;
        System.out.println( "«" + name +  " - " + author + "»");
        for (int i = 0; i < saves[numSaved].length; i++)
            System.out.println(saves[numSaved][i]);
        System.out.println();
    }

    // Удаление сохранения
    public void deleteSave(int numSaved) {
        numSaved--;

        // Сдвигаем все сохранения, которые шли до удаленного на 1 позицию влево
        for (int i = numSaved; i < savesCount - 1; i++)
            saves[i] = saves[i + 1];

        // Так как самое последнее сохранение сдвинулось на 1 влево, отчистим его предыдущее место хранения
        saves[savesCount - 1] = new String[0];

        // Уменьшаем счетчик сохранений
        savesCount--;
    }

    // Откат к сохранению
    public void returnVersion(int numSaved) {
        numSaved--;

        // Создаем новый список сохранений
        String[][] savesUpdated = new String[savesLimit][];

        // Перезаписываем текущую версию
        poem = new String[saves[numSaved].length];

        for (int i = 0; i < poem.length; i++)
            poem[i] = saves[numSaved][i];

        // В новый список сохранений перезаписываем выбранную и все более старые версии
        for (int i = 0; i < savesCount - numSaved; i++)
            savesUpdated[i] = saves[numSaved + i];

        // Обновляем наш список сохранений
        saves = savesUpdated;

        // Меняем счетчик сохранений
        savesCount = savesCount - numSaved;
    }

    // Вспомогательный метод класса для проверки на вхождение строки в данный стих
    private static boolean contains(String[] lines, String currString) {
        for (int i = 0; i < lines.length; i++) {
            if (lines[i].equals(currString))
                return true;
        }
        return false;
    }

    // Сравнение текущей версии с сохраненнной
    public void compareVersions(int numSaved) {
        // Если сравниваем с текущей (той же) версией
        if (numSaved == 0) {
            System.out.println("Версии идентичны");
            return;
        }

        numSaved--;

        // Если строка из нового стиха содержится в старом, то выводим ее
        System.out.println("Общие строки:");
        for (int i = 0; i < poem.length; i++) {
            if (contains(saves[numSaved], poem[i]))
                System.out.println(poem[i]);
        }

        System.out.println();

        // Если строка из старой версии не содержится в новой, выводим ее со знаком «-»
        System.out.println("Различные строки:");
        for (int i = 0; i < saves[numSaved].length; i++) {
            if (!contains(poem, saves[numSaved ][i]))
                System.out.println("- " + saves[numSaved][i]);
        }

        System.out.println();
        // Если строка из новой версии не содержится в старой, выводим ее со знаком «+»
        for (int i = 0; i < poem.length; i++) {
            if (!contains(saves[numSaved], poem[i]))
                System.out.println("+ " + poem[i]);
        }
        System.out.println();
    }

    // Сравнение двух сохранений
    public void compareVersions(int numSaved1, int numSaved2) {
        numSaved1--;
        numSaved2--;

        // Сначала нужно определить, какая версия более новая, а какая более старая
        String[] newer;
        String[] older;

        if (numSaved1 == numSaved2) {
            System.out.println("Версии идентичны");
            return;
        }

        if (numSaved1 < numSaved2) {
            older = saves[numSaved2];
            newer = saves[numSaved1];
        }
        else {
            older = saves[numSaved1];
            newer = saves[numSaved2];
        }

        // Аналогичное сравнение и вывод
        System.out.println("Общие строки:");
        for (int i = 0; i < newer.length; i++) {
            if (contains(older, newer[i]))
                System.out.println(newer[i]);
        }

        System.out.println();

        System.out.println("Различные строки:");
        for (int i = 0; i < older.length; i++) {
            if (!contains(newer, older[i]))
                System.out.println("- " + older[i]);
        }

        System.out.println();

        for (int i = 0; i < newer.length; i++) {
            if (!contains(older, newer[i]))
                System.out.println("+ " + newer[i]);
        }

        System.out.println();
    }
}


