## Краткое руководство по установке и запуску
*	[Скачать jar файл приложения](https://github.com/SergeevNikita/todo-list/blob/main/bin/todo-list.jar)
*	[Перейти на сайт Oracle, скачать и установить JRE в соответствии со своей ОС.](https://www.oracle.com/java/technologies/javase-jre8-downloads.html)  
	Официальная инструкция по загрузке и установке: 
	[на сайте oracle.com (на английском)](https://docs.oracle.com/javase/8/docs/technotes/guides/install/install_overview.html), 
	[на сайте java.com (на русском)](https://www.java.com/ru/download/help/download_options_ru.html).
*	[Скачать и установить базу данных PostgreSQL в соответствии со своей ОС.](https://www.enterprisedb.com/downloads/postgres-postgresql-downloads) либо использовать для установки встроенный пакетный менеджер. 
    [Инструкция по установке на Русском.](https://wiki.postgresql.org/wiki/Russian/PostgreSQL-One-click-Installer-Guide)     
    Примечание: во время установки назначить порт 5432, локаль отличную от DEFAULT (предпочтительно English USA).
*   [Запустить сервер БД](https://postgrespro.ru/docs/postgresql/12/server-start) 
*   В терминале ввести команду: psql -U postgres (или psql -U postgres -W нажать клавишу ввод и ввести пароль суперпользователя указанный при установке)
*   После удачного входа создать роль командой: CREATE ROLE "User" WITH LOGIN NOSUPERUSER CREATEDB NOCREATEROLE INHERIT NOREPLICATION CONNECTION LIMIT -1 PASSWORD 'Password';
*	Создать базу данных от имени нового пользователя: CREATE DATABASE "todo_list" WITH OWNER = "User" ENCODING = 'UTF8' CONNECTION LIMIT = -1;
* 	Выйти введя команду: \q 
*	Запустить на выполнение jar файл программы Todo List командой: java -jar [путь к файлу]./todo-list.jar
