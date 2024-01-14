# Otus-JQA_Pro_hw1

Необходимо создать проект в Maven'e и реализовать:

1. Фабрику (WebDriverFactory), которая будет получать значение из окружения и запускать соответствующий браузер: Chrome,
   Firefox или Opera.
2. Подсветку элементов перед нажатием, после нажатия вернуть данные в исходное состояние.
3. На главной странице Otus'a снизу найти список курсов (популярные курсы, специализации, рекомендации) и реализовать:
    - метод фильтр по названию курса;
    - метод выбора курса, стартующего раньше всех/позже всех (при совпадении дат - выбрать любой) при помощи reduce.
4. Реализовать движение мыши и выбор курса при помощи библиотеки Actions.