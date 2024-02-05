document.addEventListener("DOMContentLoaded", function () {
    const fetchDataButton = document.getElementById("fetchDataButton");
    const createTaskButton = document.getElementById("createTaskButton");
    const updateTaskButton = document.getElementById("updateTaskButton");
    const deleteTaskButton = document.getElementById("deleteTaskButton");

    fetchDataButton.addEventListener("click", async function fetchData() {
        const userIdInput = document.getElementById("Id").value;
        

        if (!userIdInput) {
            alert("Por favor, insira um ID de usuário válido.");
            return;
        }

        const url = `http://localhost:8080/stocks/${userIdInput}`;

        try {
            const response = await fetch(url, { method: "GET" });
            const data = await response.json();

            if (response.ok) {
                hideLoader();
                show(data);
            } else {
                console.error("Erro ao buscar dados da API:", data.error);
                alert("Erro ao buscar dados da API. Por favor, tente novamente mais tarde.");
            }
        } catch (error) {
            console.error("Erro ao buscar dados da API:", error.message);
            alert("Erro ao buscar dados da API. Por favor, tente novamente mais tarde.");
        }
    });

    // Função para criar uma nova stock
    createTaskButton.addEventListener("click", async function createTask() {
        const symbol = prompt("Symbol:");
        const company = prompt("Company Name:");
        const price = parseFloat(prompt("Price:"));

        if (!symbol || !company || !price) {
            alert("Dado invalido.");
            return;
        }

        const url = `http://localhost:8080/stocks`;
        const data = {
            symbol: symbol,
            companyName: company,
            price: price
        };

        try {
            await fetch(url, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(data)
            });

            alert("Tarefa criada com sucesso!");
        } catch (error) {
            console.error("Erro ao criar a tarefa:", error.message);
            alert("Erro ao criar a tarefa. Por favor, tente novamente mais tarde.");
        }
    });

    // Função para atualizar uma stock existente    
    updateTaskButton.addEventListener("click", async function updateTask() {
        const Id = document.getElementById("Id").value;
        const symbol = prompt("Symbol:");
        const company = prompt("Company Name:");
        const price = parseFloat(prompt("Price:"));

        if (!Id || !symbol || !company || !price) {
            alert("Por favor, insira o ID da tarefa e a nova descrição.");
            return;
        }

        const url = `http://localhost:8080/stocks/${Id}`;
        const data = {
            symbol: symbol,
            companyName: company,
            price: price
        };

        try {
            await fetch(url, {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(data)
            });

            alert("Tarefa atualizada com sucesso!");
        } catch (error) {
            console.error("Erro ao atualizar a tarefa:", error.message);
            alert("Erro ao atualizar a tarefa. Por favor, tente novamente mais tarde.");
        }
    });

    // Função para excluir uma stock existente
    deleteTaskButton.addEventListener("click", async function deleteTask() {
        const Id = document.getElementById("Id").value;

        if (!Id) {
            alert("Por favor, insira o ID da tarefa a ser excluída.");
            return;
        }

        const url = `http://localhost:8080/stocks/${Id}`;

        try {
            await fetch(url, {
                method: "DELETE"
            });

            alert("Tarefa excluída com sucesso!");
        } catch (error) {
            console.error("Erro ao excluir a tarefa:", error.message);
            alert("Erro ao excluir a tarefa. Por favor, tente novamente mais tarde.");
        }
    });
});
   


async function getAPI(url) {
    const response = await fetch(url, { method: "GET" });

    if (!response.ok) {
        throw new Error(`Erro na resposta da API: ${response.status} - ${response.statusText}`);
    }

    return await response.json();
}

function hideLoader() {
    document.getElementById("loading").style.display = "none";
}

function show(task) {
    let tab = `<thead>
            <th scope="col">ID</th>
            <th scope="col">Company Name</th>
            <th scope="col">Price</th>
            <th scope="col">Symbol</th>
        </thead>`;
    
        tab += `
                <tr>
                    <td scope="row">${task.id}</td>
                    <td>${task.symbol}</td>
                    <td>${task.companyName}</td>
                    <td>${task.price}</td>
                </tr>
            `;
    
    
    const tasksTable = document.getElementById("stocks");
    tasksTable.innerHTML = tab;
    tasksTable.style.display = "table"; // Torna a tabela visível
}