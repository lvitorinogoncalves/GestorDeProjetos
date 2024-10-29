<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt">
<head>
    <meta charset="UTF-8">
    <title>Associar Membros ao Projeto</title>
    <link rel="stylesheet" href="/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-5">
    <h3>Associar Membros ao Projeto</h3>
    <form action="/projeto/${projetoId}/adicionarMembro" method="post">
        <div class="form-group">
            <h5>Membros Disponíveis</h5>
            <c:forEach var="membro" items="${membrosDisponiveis}">
                <div class="form-check">
                    <input class="form-check-input" type="checkbox" name="membrosIds" value="${membro.id}" id="membro${membro.id}">
                    <label class="form-check-label" for="membro${membro.id}">
                        ${membro.nome}
                    </label>
                </div>
            </c:forEach>
        </div>

        <div class="form-group mt-4">
            <h5>Membros Associados</h5>
            <c:forEach var="membro" items="${membrosAssociados}">
                <p>${membro.nome}</p>
            </c:forEach>
        </div>

        <button type="submit" class="btn btn-primary mt-3">Associar Selecionados</button>
        <a href="/" class="btn btn-secondary mt-3">Voltar</a>
    </form>
</div>
</body>
</html>
