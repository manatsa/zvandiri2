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
                    <%@include file="../template/searchDateRangeFragment.jspf" %>
                    <div class="col-lg-12">
                        <div class="table-responsive">
                            <table class="itemList" class="display" cellspacing="0">
                                <thead>
                                    <tr>
                                        <th>Date of Visit</th>
                                        <th>CAT</th>
                                        <th>Patient</th>
                                        <th>Patient Age</th>
                                        <th>Patient Gender</th>
                                        <th>Support Group</th>
                                        <th>District</th>
                                        <th>Region</th>
                                        <th>Comments</th>
                                        <th>&nbsp;</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="act" items="${activities}">
                                        <tr>
                                            <td><spring:eval expression="act.dateOfVisit"/></td>
                                            <td>${act.catDetail.patient.name}</td>
                                            <td>${act.patient.name}</td>
                                            <td>${act.patient.age}</td>
                                            <td>${act.patient.gender.name}</td>
                                            <td>${act.patient.supportGroup.name}</td>
                                            <td>${act.patient.primaryClinic.district.name}</td>
                                            <td>${act.patient.primaryClinic.district.province.name}</td>
                                            <td>${act.comments}</td>
                                            <td>
                                                <a href="item.form?itemId=${act.id}">Edit</a> |
                                                <a href="item.delete?id=${act.id}">Delete</a>
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