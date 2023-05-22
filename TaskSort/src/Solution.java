/**
 * Модифицированная сортировка вставками. Массив сортируется по возрастанию, учитывая
 * элементы, которые нельзя переставлять местами. Индексы таких элементов находятся в
 * массиве с логическими значениями, где true - нельзя переставлять, false - можно.
 **/

class Solution {

    public static <T extends Comparable<T>> void modifyInsertionSort(T[] sortArr, boolean[] fixed) {
        int j;
        int k;
        //Сортировку начинаем со второго элемента, т.к. считается, что первый элемент уже отсортирован
        for (int i = 1; i < sortArr.length; i++) {
            if (fixed[i]) {
                continue;
            }
            //сохраняем ссылку на индекс предыдущего элемента
            T swap = sortArr[i];
            for (j = i, k = j - 1; j > 0 && k >= 0; ) {
                if (fixed[k]) {
                    k--;
                    continue;
                }
                if (fixed[j]) {
                    j--;
                    continue;
                }
                if (swap.compareTo(sortArr[k]) > 0 && fixed[k]) {
                    k--;
                } else if (swap.compareTo(sortArr[k]) >= 0 && !fixed[k] && i > 1) {
                    break;
                }
                //элементы отсортированного сегмента перемещаем вперёд
                sortArr[j] = sortArr[k];
                j--;
                if (k > 0) {
                    k--;
                }
            }
            sortArr[j] = swap;
        }
    }
}
