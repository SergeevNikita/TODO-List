delete
from task;
delete
from task_list;



insert into task_list(id, date_change, date_created, done, name)
values ('2d6adfc6-39a4-4c78-a39c-5f08c876db2e', '2020-10-28 14:08:12.374', '2020-10-28 14:03:54.816', 'true', 'list 1'),
       ('2d6cdfc6-39a4-4c71-a39c-5f08c876db2e', '2020-10-27 14:08:15.374', '2020-10-27 14:03:54.816', 'false', 'task_list 2'),
       ('1d6cdfc3-39a4-4c78-a39c-5f08c876db2e', '2020-10-25 15:08:15.374', '2020-10-25 15:03:54.816', 'false', 'lists');

insert into task(id, date_change, date_created, done, name, priority, title, task_list_id)
values ('2d6adfc6-39a4-4c78-a39c-1f03c876db5a', '2020-10-28 19:08:19.374', '2020-10-28 19:03:54.816', false,
        'task-1', 2, 'title 1', '2d6adfc6-39a4-4c78-a39c-5f08c876db2e'),
       ('2d6adfc6-39a1-4c78-a29c-1f03c876db7a', '2020-10-25 19:08:20.374', '2020-10-25 19:03:54.816', true,
        'task 2', 5, 'title any', '2d6adfc6-39a4-4c78-a39c-5f08c876db2e');
