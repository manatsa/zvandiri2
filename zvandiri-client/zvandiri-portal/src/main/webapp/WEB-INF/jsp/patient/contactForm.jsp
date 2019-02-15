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
                    <div class="col-lg-12">
                        <form:form commandName="item" action="${formAction}">
                            <form:hidden path="currentElement"/>
                            <%@include file="../template/formState.jspf" %>
                            <form:hidden path="patient" value="${item.patient.id}"/>
                            <form:hidden path="parent" value="${item.parent.id}"/>
                            <div class="row">
                                <div class="col-lg-6">
                                    <div class="form-group">
                                        <label>Date of Contact</label>
                                        <form:input path="contactDate" class="form-control general"/>
                                        <p class="help-block">
                                            <form:errors path="contactDate" class="alert-danger"/>
                                        </p>
                                    </div>
                                    <div class="form-group">
                                        <label>Last Clinic Appointment Date</label>
                                        <form:input path="lastClinicAppointmentDate" class="form-control general"/>
                                        <p class="help-block">
                                            <form:errors path="lastClinicAppointmentDate" class="alert-danger"/>
                                        </p>
                                    </div>
                                    <div class="form-group">
                                        <label>Attended clinic appointment</label>
                                        <form:select path="attendedClinicAppointment" class="form-control">
                                            <form:option value="" label="--Select Item"/>
                                            <form:options itemValue="code" itemLabel="name"/>
                                        </form:select>
                                        <p class="help-block">
                                            <form:errors path="attendedClinicAppointment" class="alert-danger"/>
                                        </p>
                                    </div>
                                    <div class="form-group">
                                        <label>Level of Care</label>
                                        <form:select path="careLevel" class="form-control">
                                            <form:option value="" label="--Select Item"/>
                                            <form:options itemValue="code" itemLabel="name"/>
                                        </form:select>
                                        <p class="help-block">
                                            <form:errors path="careLevel" class="alert-danger"/>
                                        </p>
                                    </div>
                                    <div class="form-group">
                                        <label>Location</label>
                                        <form:select path="location" class="form-control">
                                            <form:option value="" label="--Select Item"/>
                                            <form:options items="${locations}" itemValue="id" itemLabel="name"/>
                                        </form:select>
                                        <p class="help-block">
                                            <form:errors path="location" class="alert-danger"/>
                                        </p>
                                    </div>
                                    <div class="form-group">
                                        <label>Position</label>
                                        <form:select path="position" class="form-control">
                                            <form:option value="" label="--Select Item"/>
                                            <form:options items="${positions}" itemValue="id" itemLabel="name"/>
                                        </form:select>
                                        <p class="help-block">
                                            <form:errors path="position" class="alert-danger"/>
                                        </p>
                                    </div>
                                    <div class="form-group">
                                        <label>Reason</label>
                                        <form:select path="reason" class="form-control">
                                            <form:option value="" label="--Select Item"/>
                                            <form:options itemValue="code" itemLabel="name"/>
                                        </form:select>
                                        <p class="help-block">
                                            <form:errors path="reason" class="alert-danger"/>
                                        </p>
                                    </div>
                                    <div class="form-group">
                                        <label>Subjective</label>
                                        <form:textarea path="subjective" rows="5" class="form-control"/>
                                        <p class="help-block">
                                            <form:errors path="subjective" class="alert-danger"/>
                                        </p>
                                    </div> 
                                    <div class="form-group">
                                        <label>Objective</label>
                                        <form:textarea path="objective" rows="5" class="form-control"/>
                                        <p class="help-block">
                                            <form:errors path="objective" class="alert-danger"/>
                                        </p>
                                    </div> 
                                    <div class="form-group">
                                        <label>Plan</label>
                                        <form:textarea path="plan" rows="5" class="form-control"/>
                                        <p class="help-block">
                                            <form:errors path="plan" class="alert-danger"/>
                                        </p>
                                    </div>
                                </div>
                                <div class="col-lg-6">

                                    <c:if test="${external}">
                                        <div class="form-group">
                                            <label>External Referral</label>
                                            <form:select path="externalReferral" class="form-control">
                                                <form:option value="" label="--Select Item"/>
                                                <form:options items="${externalReferrals}" itemValue="id" itemLabel="name"/>
                                            </form:select>
                                            <p class="help-block">
                                                <form:errors path="externalReferral" class="alert-danger"/>
                                            </p>
                                        </div>
                                    </c:if>
                                    <c:if test="${internal}">
                                        <div class="form-group">
                                            <label>Internal Referral</label>
                                            <form:select path="internalReferral" class="form-control">
                                                <form:option value="" label="--Select Item"/>
                                                <form:options items="${internalReferrals}" itemValue="id" itemLabel="name"/>
                                            </form:select>
                                            <p class="help-block">
                                                <form:errors path="internalReferral" class="alert-danger"/>
                                            </p>
                                        </div>
                                    </c:if>                            
                                    <c:if test="${stable}">
                                        <div class="form-group">
                                            <label>Stable</label><br/><br/>
                                            <form:checkboxes path="stables" items="${stables}" itemLabel="name" itemValue="id" delimiter="<br/>"/>
                                            <p class="help-block">
                                                <form:errors path="stables" class="alert-danger"/>
                                            </p>
                                        </div>
                                    </c:if>
                                    <c:if test="${enhanced}">
                                        <div class="form-group">
                                            <label>Enhanced</label><br/><br/>
                                            <form:checkboxes path="enhanceds" items="${enhanceds}" itemLabel="name" itemValue="id" delimiter="<br/>"/>
                                            <p class="help-block">
                                                <form:errors path="enhanceds" class="alert-danger"/>
                                            </p>
                                        </div>
                                    </c:if>
                                    <div class="form-group">
                                        <label>Assessment</label><br/>
                                        <form:checkboxes path="assessments" items="${assessments}" itemLabel="name" itemValue="id" delimiter="<br/>"/>
                                        <p class="help-block">
                                            <form:errors path="assessments" class="alert-danger"/>
                                        </p>
                                    </div>
                                    <div class="form-group">
                                        <label>Follow Up</label>
                                        <form:select path="followUp" class="form-control">
                                            <form:option value="" label="--Select Item"/>
                                            <form:options itemValue="code" itemLabel="name"/>
                                        </form:select>
                                        <p class="help-block">
                                            <form:errors path="followUp" class="alert-danger"/>
                                        </p>
                                    </div>
                                    <div class="form-group">
                                        <label>Action Taken</label><br/><br/>
                                        <form:select path="actionTaken" class="form-control">
                                            <form:option value="" label="--Select Item"/>
                                            <form:options items="${actionTaken}" itemValue="id" itemLabel="name"/>
                                        </form:select>
                                        <p class="help-block">
                                            <form:errors path="actionTaken" class="alert-danger"/>
                                        </p>
                                    </div>
                                    <c:if test="${internalStaff}">
                                        <div class="form-group">
                                            <label>Zvandiri Staff Member</label><br/><br/>
                                            <form:select path="referredPerson" class="form-control">
                                                <form:option value="" label="--Select Item"/>
                                                <form:options items="${staff}" itemValue="id" itemLabel="displayName"/>
                                            </form:select>
                                            <p class="help-block">
                                                <form:errors path="referredPerson" class="alert-danger"/>
                                            </p>
                                        </div>
                                    </c:if>
                                </div>
                            </div>
                            <div class="form-group">
                                <button class="btn btn-primary" type="submit">Save</button>
                                <a href="${page}/patient/dashboard/profile.htm?id=${patient.id}"><button class="btn btn-primary" type="button">Cancel</button></a>
                            </div>
                        </form:form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="../template/footer.jspf" %>
<script type="text/javascript">
    $("#reason").change(function () {
        $("#currentElement").val($(window).scrollTop());
        $("form").attr("action", "reload-form").submit();
    });
    
    $("#actionTaken").change(function () {
        $("#currentElement").val($(window).scrollTop());
        $("form").attr("action", "reload-form").submit();
    });
    $(window).scrollTop("<c:out value="${item.currentElement}"/>");
    //actionTaken
</script>