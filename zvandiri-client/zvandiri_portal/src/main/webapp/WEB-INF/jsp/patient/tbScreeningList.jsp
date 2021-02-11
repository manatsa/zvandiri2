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
                    <div class="col-lg-10">
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
                        <b class="titleHeader">TB Screening Details</b><hr/>
                        <c:if test="${canEdit}"><a href="${page}/patient/tb-screening/item.form?patientId=${patient.id}">Add TB Screening </a></c:if>
                        <div class="table-responsive">
                            <table class="itemList" class="display" cellspacing="0">
                                <thead>
                                <tr>
                                    <th>Screened For TB</th>
                                    <th>Date Screened</th>
                                    <th>TB Sysmptoms</th>
                                    <th>Identified With TB</th>
                                    <th>Date Started Treatment</th>
                                    <th>Treatment Outcome</th>
                                    <th>Referred For IPT</th>
                                    <th>On IPT</th>
                                    <th>Date Started IPT</th>
                                    <th>Date Created</th>
                                    <th>&nbsp;</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="item" items="${items}">
                                    <tr>
                                        <td>${item.screenedForTb.name}</td>
                                        <td><spring:eval expression="item.dateScreened"/></td>
                                        <td>${item.getSymptoms()}</td>
                                        <td>${item.identifiedWithTb.name}</td>
                                        <td><spring:eval expression="item.dateStartedTreatment"/></td>
                                        <td>${item.tbTreatmentOutcome.name}</td>
                                        <td>${item.referredForIpt.name}</td>
                                        <td>${item.onIpt.name}</td>
                                        <td><spring:eval expression="item.dateStartedIpt"/></td>
                                        <td><spring:eval expression="item.dateCreated"/></td>
                                        <td>
                                            <a href="${page}/patient/tb-screening/item.form?id=${item.id}">Edit</a>
                                                <%--                                                <c:if test="${canEdit}"><a href="item.delete?id=${infection.id}">Delete</a></c:if>--%>
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