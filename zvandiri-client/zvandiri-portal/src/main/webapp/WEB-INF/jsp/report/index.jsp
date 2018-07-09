<%@include file="../template/header.jspf" %>
<form:form commandName="item">
    <legend style="color: green">Reports Dash Board</legend> 

    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                View and Export Aggregate Reports
            </div>
            <div class="panel-body">
                <div class="table-responsive">
                    <table width="100%" class="table table-bordered">
                        <tbody>
                            <tr>
                                <td>
                                    <sec:authorize access="
                                                   hasRole('ROLE_ADMINISTRATOR') or 
                                                   hasRole('ROLE_DATA_CLERK') or 
                                                   hasRole('ROLE_M_AND_E_OFFICER') or
                                                   hasRole('ROLE_HOD_M_AND_E')
                                                   ">
                                        <a href="${page}/report/pivot/patient/index.htm">Patient Pivot Report</a>
                                    </sec:authorize>
                                </td>
                                <td>
                                    <a href="${page}/report/pivot/contact/index.htm">Contact Pivot Reports</a>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <a href="${page}/report/pivot/patient/patients-exiting-program.htm">Patients Exiting Program Pivot Report</a>
                                </td>
                                <td>
                                    <sec:authorize access="
                                                   hasRole('ROLE_ADMINISTRATOR') or 
                                                   hasRole('ROLE_DATA_CLERK') or 
                                                   hasRole('ROLE_M_AND_E_OFFICER') or
                                                   hasRole('ROLE_HOD_M_AND_E')
                                                   ">
                                        <a href="${page}/report/export-database/index.htm">Export Database</a>
                                    </sec:authorize>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <sec:authorize access="
                                                   hasRole('ROLE_ADMINISTRATOR') or 
                                                   hasRole('ROLE_ZM') or 
                                                   hasRole('ROLE_M_AND_E_OFFICER') or
                                                   hasRole('ROLE_HOD_M_AND_E') or 
                                                   hasRole('ROLE_MANAGEMENT')
                                                   ">
                                        <a href="${page}/report/problem/problem-report.htm">Combined Statistical Report</a>
                                    </sec:authorize>
                                </td>
                                <sec:authorize access="
                                               hasRole('ROLE_ADMINISTRATOR') or 
                                               hasRole('ROLE_ZM') or 
                                               hasRole('ROLE_M_AND_E_OFFICER') or
                                               hasRole('ROLE_HOD_M_AND_E')
                                               ">
                                    <td>
                                        <a href="${page}/report/referral/detailed.htm">Referral Detailed Report</a>
                                    </td>
                                </sec:authorize>
                            </tr>
                            <tr>
                                <td>
                                    <a href="${page}/report/contact/detailed.htm">Contact Detailed Report</a>
                                </td>
                                <td>
                                    <a href="${page}/report/aggregate/contact-carelevel-healthcenter.htm">Contact Care Level Cross Tabulation (Health Center)</a>
                                </td>
                            </tr>
                            <tr>                                
                                <td>
                                    <a href="${page}/report/aggregate/data-entry.htm">Data Entry Report</a>
                                </td>
                                <td>
                                    <a href="${page}/report/aggregate/contact-entry.htm">Contact Entry Report</a>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <a href="${page}/report/detailed/range.htm">Beneficiaries By Date Range</a>
                                </td>
                                <td>&nbsp;</td>
                            </tr>
                            <tr>
                                <th colspan="2">Visual Reports</th>
                            </tr>
                            <tr>
                                <td><a href="${page}/report/graphs/contact-distribution">Number Of Contacts Past 6 Months</a></td>
                                <td><a href="${page}/report/graphs/contact-level-of-care">Contact Trends By Care Level</a></td>
                            </tr>
                            <tr>
                                <td><a href="${page}/report/graphs/patient-gender-distribution">Patient Distribution By Gender</a></td>
                                <td><a href="${page}/report/graphs/patient-age-group-distribution.htm">Patient Distribution By Age Group</a></td>
                            </tr>
                            <tr>
                                <td><a href="${page}/report/graphs/patient-status-distribution">Patient Distribution By Status</a></td>
                                <td><a href="${page}/report/graphs/referral-distribution">Number of External Patient Referrals Past 6 Months</a></td>
                            </tr>
                            <tr>
                                <td><a href="${page}/report/graphs/contact-care-level-distribution">Contact Distribution By Care Level</a></td>
                                <td><a href="${page}/report/graphs/patient-contact-distribution">Patient Distribution By Contact</a></td>
                            </tr>
                            <tr>
                                <td><a href="${page}/report/graphs/patient-viral-load-distribution">Patient Distribution By Viral Load</a></td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</form:form>
<%@include file="../template/footer.jspf" %>
