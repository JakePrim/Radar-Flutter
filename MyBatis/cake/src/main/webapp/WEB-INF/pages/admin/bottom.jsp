<%--
  Created by IntelliJ IDEA.
  User: prim
  Date: 2020/3/6
  Time: 2:30 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
</div>
<style>
    /* demo page styles */
    body { min-height: 2300px; }

    .content-header b,
    .admin-form .panel.heading-border:before,
    .admin-form .panel .heading-border:before {
        transition: all 0.7s ease;
    }
    /* responsive demo styles */
    @media (max-width: 800px) {
        .admin-form .panel-body { padding: 18px 12px; }
    }
</style>

<style>
    .ui-datepicker select.ui-datepicker-month,
    .ui-datepicker select.ui-datepicker-year {
        width: 48%;
        margin-top: 0;
        margin-bottom: 0;

        line-height: 25px;
        text-indent: 3px;

        color: #888;
        border-color: #DDD;
        background-color: #FDFDFD;

        -webkit-appearance: none; /*Optionally disable dropdown arrow*/
    }
</style>

<!-- jQuery -->
<script src="../../../js/jquery.min.js"></script>
<script src="../../../js/jquery-ui.min.js"></script>
<!-- Theme Javascript -->
<script src="../../../js/utility.js"></script>
<script src="../js/demo/demo.js"></script>
<script src="../../../js/main.js"></script>
<script src="../../../js/pages.js"></script>
<!-- END: PAGE SCRIPTS -->
</body>
<script>
    $("#add_item_button").click(function(){
        $("#batch_items").children("div").last().after($("#batch_items").children("div").first().clone());
        $("#batch_items").find("button.remove_item_button").attr("disabled",false);
        $("#batch_items").children("div").last().find("input").eq(0).attr("value","");
        $("#batch_items").children("div").last().find("button.remove_item_button").click(
            function(){itemRemove(this);}
        );
    });
    function itemRemove(ele){
        $(ele).parent().parent().remove();
        if($("#batch_items").children("div").size()==1){
            $("#batch_items").children("div").find("button.remove_item_button").attr("disabled",true);
        }
    }
</script>

</html>