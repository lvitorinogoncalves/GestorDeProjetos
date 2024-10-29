<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Gestor de Projetos</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/index.css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container mt-5">
    <h2>Gestão de Projetos</h2>

    <c:if test="${not empty mensagem}">
        <div class="alert alert-info" role="alert">
            ${mensagem}
        </div>
    </c:if>

    <table class="table table-striped">
        <thead>
            <tr>
                <th>Nome</th>
                <th>Data de Início</th>
                <th>Status</th>
                <th>Ações</th>
            </tr>
        </thead>
        <tbody>
        <c:forEach var="projeto" items="${projetos}">
            <tr>
                <td>${projeto.nome}</td>
                <td>${projeto.dataInicioFormatada}</td>
                <td>
                    <c:choose>
                        <c:when test="${projeto.status == 'EM_ANALISE'}">Em Análise</c:when>
                        <c:when test="${projeto.status == 'ANALISE_REALIZADA'}">Análise Realizada</c:when>
                        <c:when test="${projeto.status == 'ANALISE_APROVADA'}">Análise Aprovada</c:when>
                        <c:when test="${projeto.status == 'INICIADO'}">Iniciado</c:when>
                        <c:when test="${projeto.status == 'PLANEJADO'}">Planejado</c:when>
                        <c:when test="${projeto.status == 'EM_ANDAMENTO'}">Em Andamento</c:when>
                        <c:when test="${projeto.status == 'ENCERRADO'}">Encerrado</c:when>
                        <c:when test="${projeto.status == 'CANCELADO'}">Cancelado</c:when>
                        <c:otherwise>Desconhecido</c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <a href="${pageContext.request.contextPath}/projeto/editar/${projeto.id}" class="btn btn-primary">Editar</a>
                    <form action="${pageContext.request.contextPath}/projeto/excluir/${projeto.id}" method="post" style="display:inline;">
                        <button type="submit" class="btn btn-danger" onclick="return confirm('Tem certeza que deseja excluir este projeto?');">Excluir</button>
                    </form>
                    <a href="${pageContext.request.contextPath}/associar-membros.jsp?projetoId=${projeto.id}" class="btn btn-secondary">
                        Associar Membros
                    </a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <a href="${pageContext.request.contextPath}/projeto/cadastrar" class="btn btn-success">Cadastrar Novo Projeto</a>
</div>
</body>
</html>
