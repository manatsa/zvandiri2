<%@include file="../template/header.jspf" %>
<div class="col-lg-12">
    <div class="panel panel-default">
        <div class="panel-heading">
            ${pageTitle}
        </div>
        <div class="panel-body">
            <a href="${page}/report/index.htm">&DoubleLeftArrow; Back To Reports DashBoard Home</a><br/>
            <%@include file="../template/searchDateRangeFragment.jspf" %>
            <div class="row">
                <div class="col-lg-12">
                    <table id="tableList" class="display" cellspacing="0">
                        <thead>
                        <th>Name</th>
                        <th>UAC</th>
                        <th>Age</th>
                        <th>Viral Load</th>
                        <th>CD4 Count</th>
                        <th>Date of Birth</th>
                        <th>Gender</th>
                        <th>Date Joined</th>
                        <th>Region</th>
                        <th>District</th>
                        <th>Primary Clinic</th>
                        <th>Support Group</th>
                        <th>Mobile Number</th>
                        <th>Referrer</th>
                        </thead>
                        <tfoot>
                        <th>Name</th>
                        <th>UAC</th>
                        <th>Age</th>
                        <th>Viral Load</th>
                        <th>CD4 Count</th>
                        <th>Date of Birth</th>
                        <th>Gender</th>
                        <th>Date Joined</th>
                        <th>Region</th>
                        <th>District</th>
                        <th>Primary Clinic</th>
                        <th>Support Group</th>
                        <th>Mobile Number</th>
                        <th>Referrer</th>
                        </tfoot>
                        <tbody>
                        <c:forEach var="item" items="${items}" >
                            <tr>
                                <td>${item.patient.name}</td>
                                <td>${item.patient.patientNumber}</td>
                                <td>${item.patient.age}</td>
                                <td>${item.patient.viralLoad}</td>
                                <td>${item.patient.cd4Count}</td>
                                <td><spring:eval expression="item.patient.dateOfBirth"/></td>
                                <td>${item.patient.gender.name}</td>
                                <td><spring:eval expression="item.patient.dateJoin"/></td>
                                <td>${item.patient.primaryClinic.district.province.name}</td>
                                <td>${item.patient.primaryClinic.district.name}</td>
                                <td>${item.patient.primaryClinic.name}</td>
                                <td>${item.patient.supportGroup.name}</td>
                                <td>${item.patient.mobileNumber}</td>
                                <td>${item.patient.referer.name}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>

        </div>
        <div class="panel-footer" style="text-align: right">
            Export/ View As
            <a href="${page}${excelExport}">
                <img src="<c:url value="/resources/images/excel.jpeg"/>"/>
            </a>
        </div>
    </div>
</div>
<%@include file="../template/footer.jspf" %>
<script type="text/javascript">
    $(".sidebar-nav").addClass("custom-side-bar-ref");
    $("#page-wrapper").addClass("main-wrp");
    // ensire toggle side bar is pointing right
    $("span.toggle-span").addClass("fa-long-arrow-right");
    $("span.toggle-span").removeClass("fa-long-arrow-left");
</script>