<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>${projeto.id != null ? 'Editar' : 'Cadastro'} de Projeto</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/cadastro-projeto-form.css">
</head>
<body>
<div class="container">
    <h1>${projeto.id != null ? 'Editar' : 'Cadastro'} de Projeto</h1>
    <form:form action="${projeto.id != null ? '/projeto/atualizar' : '/projeto/salvar'}" method="post" modelAttribute="projeto" class="form">

        <form:hidden path="id" />

        <div class="form-group">
            <label for="nome">Nome do Projeto</label>
            <form:input path="nome" id="nome" />
        </div>

        <div class="form-group">
            <label for="dataInicio">Data de Início</label>
            <form:input path="dataInicio" id="dataInicio" type="date" />
        </div>

        <div class="form-group">
            <label for="dataPrevisaoFim">Previsão de Término</label>
            <form:input path="dataPrevisaoFim" id="dataPrevisaoFim" type="date" />
        </div>

        <div class="form-group">
            <label for="dataFim">Data real de Término</label>
            <form:input path="dataFim" id="dataFim" type="date" />
        </div>

        <div class="form-group">
            <label for="idGerente">Gerente</label>
            <form:select path="idGerente" id="idGerente">
                <form:options items="${gerentes}" itemValue="id" itemLabel="nome" />
            </form:select>
        </div>

        <div class="form-group">
            <label for="orcamento">Orçamento</label>
            <form:input path="orcamento" id="orcamento" type="text" onkeyup="mascaraMoeda(this)" />
        </div>

        <div class="form-group">
            <label for="descricao">Descrição</label>
            <form:textarea path="descricao" id="descricao" />
        </div>

        <div class="form-group">
            <label for="status">Status</label>
            <form:select path="status" id="status">
                <form:option value="EM_ANALISE" label="Em Análise"/>
                <form:option value="ANALISE_REALIZADA" label="Análise Realizada"/>
                <form:option value="ANALISE_APROVADA" label="Análise Aprovada"/>
                <form:option value="INICIADO" label="Iniciado"/>
                <form:option value="PLANEJADO" label="Planejado"/>
                <form:option value="EM_ANDAMENTO" label="Em Andamento"/>
                <form:option value="ENCERRADO" label="Encerrado"/>
                <form:option value="CANCELADO" label="Cancelado"/>
            </form:select>
        </div>

        <div class="form-group">
            <label for="risco">Risco</label>
            <form:select path="risco" id="risco">
                <form:option value="BAIXO" label="Baixo"/>
                <form:option value="MEDIO" label="Médio"/>
                <form:option value="ALTO" label="Alto"/>
            </form:select>
        </div>

        <button type="submit">${projeto.id != null ? 'Atualizar' : 'Salvar'} Projeto</button>
    </form:form>
</div>

<script>
    function mascaraMoeda(campo) {
        let valor = campo.value.replace(/\D/g, '');
        valor = (valor / 100).toFixed(2) + '';
        valor = valor.replace(".", ",");
        valor = valor.replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1.');
        campo.value = 'R$ ' + valor;
    }

    function removerMascaraMoeda(campo) {
        let valor = campo.value.replace("R$ ", "").replace(".", "").replace(",", ".");
        campo.value = valor;
    }

    document.querySelector('form').addEventListener('submit', function(event) {
        const orcamentoField = document.getElementById('orcamento');
        removerMascaraMoeda(orcamentoField);
    });
</script>

</body>
</html>
