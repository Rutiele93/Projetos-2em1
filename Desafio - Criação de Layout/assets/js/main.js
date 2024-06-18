function addTask(period) {
    var taskInput = document.getElementById('new-task-' + period);
    var taskList = document.getElementById('task-list-' + period);
    if (taskInput.value !== '') {
        var newTask = document.createElement('li');
        newTask.innerText = taskInput.value;
        newTask.innerHTML += ' <button onclick="removeTask(this)" class="btn btn-danger btn-xs">Remover</button>';
        taskList.appendChild(newTask);
        taskInput.value = '';
    }
}

function removeTask(button) {
    button.parentElement.remove();
}
