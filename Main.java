import java.util.Scanner;
import java.io.PrintStream;
import lab3.GitPoem;

public class Main {
    public static PrintStream out = System.out;
    public static Scanner in = new Scanner(System.in);
    public static void main(String[] args) {
        // Создаем объект класса GitPoem - наш стих; лимит сохранений - 3
        GitPoem poem = new GitPoem("Бородино", "М.Ю. Лермонтов", 3);

        // Изначально строк в стихе нет
        out.print(poem);
        // Запишем строки нашего стиха (их 7)
        String[] strings = {
                "– Скажи-ка, дядя, ведь недаром",
                "Москва, спалённая пожаром,",
                "Французу отдана?",
                "Ведь были ж схватки боевые,",
                "Да, говорят, ещё какие!",
                "Недаром помнит вся Россия",
                "Про день Бородина!"
        };
        for (int i = 0; i < 7; i++) {
            poem.addString(strings[i]);
        }

        // Выведем полученный стих
        out.print(poem);

        // Удалим третью строку - "Французу отдана?"
        poem.deleteString(3);
        out.print(poem);

        // Вставим ее обратно
        poem.insertString("Французу отдана?", 3);
        out.print(poem);

        // Сохраним исходную версию и добавим еще 2 строки к ней
        poem.save();
        poem.addString("– Да, были люди в наше время,");
        poem.addString("Не то, что нынешнее племя:");

        // Текущая версия изменилась, однако мы можем увидеть исходную, благодаря сохранению
        out.println("Последнее сохранение:\n");
        poem.printSave(1);

        // Сохраним текущую версию, добавим еще 2 строки и опять сохраним
        poem.save();
        poem.addString("Богатыри – не вы!");
        poem.addString("Плохая им досталась доля:");
        poem.save();
        // Мы достигли лимита сохранений
        // Добавим еще 2 строки и опять сохраним, а затем обратимся к самому старому сохранению
        poem.addString("Немногие вернулись с поля…");
        poem.addString("Не будь на то господня воля,");
        poem.save();
        // Как мы можем увидеть, самая старая версия (исходная) удалилась
        poem.printSave(3);

        // Удалим последнее сохранение
        poem.deleteSave(1);
        // Теперь предпоследнее сохранение стало последним, а также освободилось место для еще одного
        poem.printSave(1);
        poem.printSave(3);

        // Вернемся к самой старой версии - теперь это самое последнее сохранение
        poem.returnVersion(2);
        out.print(poem);

        // Добавим еще 2 строки и удалим первые 2, а затем сравним их
        poem.addString("Богатыри – не вы!");
        poem.addString("Плохая им досталась доля:");
        poem.deleteString(1);
        poem.deleteString(1);
        poem.compareVersions(1);

        // Сохраним текущую версию и сравним с предыдущее (то же самое сравнение;
        // просто проверка, что сравнение двух сравнений работает корректно)
        poem.save();
        poem.compareVersions(1,2);
    }
}