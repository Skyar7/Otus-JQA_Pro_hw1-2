##language: ru
#
#@main_page_courses_by_name
#Функционал: Выбор курсов по названию
#
#  Структура сценария: Поиск курса и проверка наличия на его странице имени и описания
#    Пусть Используется браузер <browser>
#    Если Открыта главная страница
#    И Найден и открыт курс с названием <requiredCourseName>
#    Тогда На странице курса будет его имя и описание
#    Примеры:
#      | browser  | requiredCourseName |
#      | "Chrome" | "Специализация"    |
#      | "Chrome" | "QA"               |