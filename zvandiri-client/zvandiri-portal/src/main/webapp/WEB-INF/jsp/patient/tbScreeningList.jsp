<%@include file="../template/header.jspf" %>
<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                ${pageTitle}
            </div>
            <div class="panel-body">
                <%@include file="../template/message.jspf" %>
                <div class="row">
                    <div class="col-lg-12">
                        <b class="titleHeader">TB Screening Detail</b> | <a href="item.form?personId=${person.id}">Add TB Screening Item </a>
                        <hr/>
                        <div class="table-responsive">
                            <table class="itemList" class="display" cellspacing="0">
                                <thead>
                                    <tr>
                                        <th>Are You Coughing?</th>
                                        <th>Are You Sweating?</th>
                                        <th>Have you had Unintentional weight loss in last 6 months?</th>
                                        <th>Have you had a fever ?</th>
                                        <th>Currently On TB Treatment?</th>
                                        <th>Type Of Drug</th>
                                        <th>TB Outcome</th>
                                        <th>TB Contacts Page</th>
                                        <th>TB Treatment Outcome</th>
                                        <th>&nbsp;</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="item" items="${items}">
                                        <tr>
                                            <td>${item.coughing}</td>
                                            <td>${item.sweating}</td>
                                            <td>${item.weightLoss}</td>
                                            <td>${item.fever}</td>
                                            <td>${item.currentlyOnTreatment}</td>
                                            <td>${item.typeOfDrug}</td>
                                            <td>${item.tbOutcome}</td>
                                            <td>${item.tbTreatmentStatus}</td>
                                            <td>${item.tbTreatmentOutcome}</td>
                                            <td>
                                                <a href="item.form?id=${item.id}">Edit</a> |
                                                <a href="item.delete?id=${item.id}">Delete</a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="../template/footer.jspf" %>