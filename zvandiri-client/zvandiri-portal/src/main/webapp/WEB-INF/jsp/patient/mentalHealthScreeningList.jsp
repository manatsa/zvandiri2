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
                        <div class="panel panel-default">                            
                            <%@include file="../template/dashboard/patientProfile.jspf" %>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-lg-6"><a href="${page}/patient/dashboard/profile.htm?id=${patient.id}">&DoubleLeftArrow; Back To ${patient.name} Dashboard</a></div>
                </div> 
                <br/>
                <div class="row">
                    <div class="col-lg-12">
                        <b class="titleHeader">Mental Health Screening Detail</b><hr/>
                        <c:if test="${item != null}">
                            <table class="table-condensed" width="100%">
                                <tr>
                                    <th>Screened For Mental Health</th>
                                    <td>${item.screenedForMentalHealth}</td>
                                </tr>
                                <tr>
                                    <th>Identified Risks</th>
                                    <td>${item.identifiedRisk}</td>
                                </tr>
                                <tr>
                                    <th>Action Taken</th>
                                    <td>${item.actionTaken.name}</td>
                                </tr>
                                <tr>
                                    <th>Other</th>
                                    <td>${item.actionTakenText}</td>
                                </tr>
                                <tr>
                                    <th>Rescreened For Mental Health</th>
                                    <td>${item.mentalScreenResult}</td>
                                </tr>
                                <tr>
                                    <th>Identified Risks</th>
                                    <td>${item.rescreenIdentifiedRisk}</td>
                                </tr>
                                <tr>
                                    <th>Action Taken</th>
                                    <td>${item.rescreenActionTaken.name}</td>
                                </tr>
                                <td>&nbsp;</td>
                                <td>
                                    <a href="${page}/beneficiary/mental-health-screening/item.form?itemId=${item.id}">Edit</a> | 
                                    <c:if test="${canEdit}"><a href="${page}/beneficiary/mental-health-screening/item.delete?itemId=${item.id}">Delete</a></c:if>
                                    </td>
                                    </tr>
                                </table>            
                        </c:if>
                        <c:if test="${item == null}">
                            <c:if test="${canEdit}"><a href="${page}/beneficiary/mental-health-screening/item.form?patientId=${patient.id}">Add Mental Health Screening </a></c:if>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="../template/footer.jspf" %>