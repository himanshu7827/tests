
var Transfer = (function ($) {

    var selected_total_num = 0;
   
    var currentTimeStr = (new Date()).getTime() + parseInt(10000 * Math.random());
 
    var inputId = "";

    function transfer(settings) {

        inputId = settings.inputId;
        
        var itemName = settings.itemName;
        
        var groupItemName = settings.groupItemName;
        
        var groupListName = settings.groupListName;
        
        var valueName = settings.valueName;
        
        var container = "." + settings.container;
        
        var callable = settings.callable;
        
        var transferId = "#transfer_double_" + inputId;

        
        var selectInputId = "#" + inputId;

        
        var data = settings.data || [];
        
        var groupData = settings.groupData || [];

       
        var total_num = settings.data.length;
        
        var total_num_str = settings.data.length + " Items";

        
        var total_group_num = getGroupNum(groupData, groupListName);
        
        var total_group_num_str = total_group_num + " Items";

        
        var new_total_num = 0;
        
        var new_group_total_num = 0;

        
        var tabItemName = ".tab-item-name-" + currentTimeStr;
        
        var transferDoubleList = ".transfer-double-list-" + currentTimeStr;

        
        var listSearchId = "#listSearch_" + currentTimeStr;
        
        var groupListSearchId = "#groupListSearch_" + currentTimeStr;
       
        var selectedListSearchId = "#selectedListSearch_" + currentTimeStr;

        
        var tabContentFirst = ".tab-content-first-" + currentTimeStr;
        
        var transferDoubleListUl = ".transfer-double-list-ul-" + currentTimeStr;
       
        var transferDoubleListLi = ".transfer-double-list-li-" + currentTimeStr;
        
        var checkboxItem = ".checkbox-item-" + currentTimeStr;
        
        var checkboxName = ".checkbox-name-" + currentTimeStr;
        
        var totalNum = ".total_num_" + currentTimeStr;
        
        var selectAllId = "#selectAll_" + currentTimeStr;

        
        var transferDoubleGroupListUl = ".transfer-double-group-list-ul-" + currentTimeStr;
        
        var transferDoubleGroupListLi = ".transfer-double-group-list-li-" + currentTimeStr;
        
        var groupSelectAll = ".group-select-all-" + currentTimeStr;
        
        var groupName = ".group-name-" + currentTimeStr;
        
        var transferDoubleGroupListLiUl = ".transfer-double-group-list-li-ul-" + currentTimeStr;
        
        var transferDoubleGroupListLiUlLi = ".transfer-double-group-list-li-ul-li-" + currentTimeStr;
        
        var groupCheckboxItem = ".group-checkbox-item-" + currentTimeStr;
        
        var groupCheckboxName = ".group-checkbox-name-" + currentTimeStr;
       
        var groupTotalNum = ".group_total_num_" + currentTimeStr;
        
        var groupsSelectAllId = "#groupsSelectAll_" + currentTimeStr;

        
        var transferDoubleSelectedListUl = ".transfer-double-selected-list-ul-" + currentTimeStr;
       
        var transferDoubleSelectedListLi = ".transfer-double-selected-list-li-" + currentTimeStr;
       
        var checkboxSelectedItem = ".checkbox-selected-item-" + currentTimeStr;
      
        var checkboxSelectedName = ".checkbox-selected-name-" + currentTimeStr;
       
        var selectedAllId = "#selectedAll_" + currentTimeStr;
        
        var selectedTotalNum = ".selected_total_num_" + currentTimeStr;

        
        var addSelected = "#add_selected_" + currentTimeStr;
        
        var deleteSelected = "#delete_selected_" + currentTimeStr;


     
        $(container).append(generateTransfer(inputId, currentTimeStr));

      
        $(transferId).find(transferDoubleListUl).empty();
        $(transferId).find(transferDoubleListUl).append(generateLeftList(currentTimeStr, data, itemName, valueName));
        $(transferId).find(totalNum).empty();
        $(transferId).find(totalNum).append(total_num_str);

        $(transferId).find(transferDoubleGroupListUl).empty();
        $(transferId).find(transferDoubleGroupListUl).append(generateLeftGroupList(currentTimeStr, groupData, itemName, groupListName, groupItemName, valueName));
        $(transferId).find(groupTotalNum).empty();
        $(transferId).find(groupTotalNum).append(total_group_num_str);

       
        $(transferId).find(tabItemName).on("click", function () {
            $(selectAllId).prop("checked", false);
            if (!$(this).is(".tab-active")) {
                $(this).addClass("tab-active").siblings().removeClass("tab-active");
                $(transferDoubleList).eq($(transferId).find(tabItemName).index(this)).addClass("tab-content-active").siblings().removeClass("tab-content-active");
                $(transferId).find(".checkbox-normal").each(function () {
                    $(this).prop("checked", false);
                });
                $(addSelected).removeClass("btn-arrow-active");
                $(transferId).find(transferDoubleSelectedListUl).empty();
                
                $(transferId).find(selectedTotalNum).text("共0 Items");
               
                if ($(transferId).find(tabContentFirst).css("display") != "none") {
                    $(transferId).find(transferDoubleGroupListLiUlLi).each(function () {
                        $(this).css('display', 'block');
                    });
                    $(transferId).find(groupCheckboxItem).each(function () {
                        $(this).prop("checked", false);
                    });

                    $(transferId).find(selectAllId).prop("disabled", "");

                    $(transferId).find(groupTotalNum).empty();
                    $(transferId).find(groupTotalNum).append($(transferId).find(transferDoubleGroupListLiUlLi).length + " Items");
                } else {
                    
                    for (var j = 0; j < $(transferId).find(groupSelectAll).length; j++) {
                        $(transferId).find(groupSelectAll).eq(j).prop("disabled", "");
                    }
                    $(transferId).find(groupsSelectAllId).prop("disabled", "");

                    $(transferId).find(transferDoubleListLi).each(function () {
                        $(this).css('display', 'block');
                    });
                    $(transferId).find(checkboxItem).each(function () {
                        $(this).prop("checked", false);
                    });
                    $(transferId).find(totalNum).empty();
                    $(transferId).find(totalNum).append($(transferId).find(transferDoubleListLi).length + " Items");
                }
                
                callable.call(this, getSelected(), getSelectedName());
                
                $(addSelected).removeClass("btn-arrow-active");
                $(deleteSelected).removeClass("btn-arrow-active");
            }
        });

       
        $(transferId).on("click", checkboxItem, function () {
            var selected_num = 0;
            for (var i = 0; i < $(transferId).find(checkboxItem).length; i++) {
                if ($(transferId).find(transferDoubleListLi).eq(i).css('display') != "none" && $(transferId).find(checkboxItem).eq(i).is(':checked')) {
                    selected_num++;
                }
            }
            if (selected_num > 0) {
                $(addSelected).addClass("btn-arrow-active");
            } else {
                $(addSelected).removeClass("btn-arrow-active");
            }
        });

        
        $(transferId).on("click", groupCheckboxItem, function () {
            var selected_num = 0;
            for (var i = 0; i < $(transferId).find(groupCheckboxItem).length; i++) {
                if ($(transferId).find(transferDoubleGroupListLiUlLi).eq(i).css('display') != "none" && $(transferId).find(groupCheckboxItem).eq(i).is(':checked')) {
                    selected_num++;
                }
            }
            if (selected_num > 0) {
                $(addSelected).addClass("btn-arrow-active");
            } else {
                $(addSelected).removeClass("btn-arrow-active");
            }
        });

        
        $(transferId).on("click", checkboxSelectedItem, function () {
            var deleted_num = 0;
            for (var i = 0; i < $(transferId).find(checkboxSelectedItem).length; i++) {
                if ($(transferId).find(checkboxSelectedItem).eq(i).is(':checked')) {
                    deleted_num++;
                }
            }
            if (deleted_num > 0) {
                $(deleteSelected).addClass("btn-arrow-active");
            } else {
                $(deleteSelected).removeClass("btn-arrow-active");
            }
        });

        
        $(groupSelectAll).on("click", function () {
            
            var groupIndex = ($(this).attr("id")).split("_")[1];
            
            if ($(this).is(':checked')) {
               
                $(addSelected).addClass("btn-arrow-active");
                for (var i = 0; i < $(transferId).find(".belongs-group-" + groupIndex + "-" + currentTimeStr).length; i++) {
                    if (!$(transferId).find(".belongs-group-" + groupIndex + "-" + currentTimeStr).eq(i).is(':checked') && $(transferId).find(".belongs-group-" + groupIndex + "-" + currentTimeStr).eq(i).parent().parent().css("display") != "none") {
                       
                        $(transferId).find(".belongs-group-" + groupIndex + "-" + currentTimeStr).eq(i).prop("checked", true);
                    }
                }
                var groupCheckedNum = 0;
                $(transferId).find(groupSelectAll).each(function () {
                    if ($(this).is(":checked")) {
                        groupCheckedNum = groupCheckedNum + 1;
                    }
                });
                if (groupCheckedNum == $(transferId).find(groupSelectAll).length) {
                    $(groupsSelectAllId).prop("checked", true);
                }
            } else {
                for (var j = 0; j < $(transferId).find(".belongs-group-" + groupIndex + "-" + currentTimeStr).length; j++) {
                    if ($(transferId).find(".belongs-group-" + groupIndex + "-" + currentTimeStr).eq(j).is(':checked') && $(transferId).find(".belongs-group-" + groupIndex + "-" + currentTimeStr).eq(i).parent().parent().css("display") != "none") {
                        $(transferId).find(".belongs-group-" + groupIndex + "-" + currentTimeStr).eq(j).prop("checked", false);
                    }
                }
                var groupCheckedNum = 0;
                $(transferId).find(groupSelectAll).each(function () {
                    if ($(this).is(":checked")) {
                        groupCheckedNum = groupCheckedNum + 1;
                    }
                });
                if (groupCheckedNum != $(transferId).find(groupSelectAll).length) {
                    $(groupsSelectAllId).prop("checked", false);
                }
                if (groupCheckedNum == 0) {
                    $(addSelected).removeClass("btn-arrow-active");
                }
            }
        });

     
        $(selectAllId).on("click", function () {
            if ($(this).is(':checked')) {
                for (var i = 0; i < $(transferId).find(checkboxItem).length; i++) {
                    if ($(transferId).find(transferDoubleListLi).eq(i).css('display') != "none" && !$(transferId).find(checkboxItem).eq(i).is(':checked')) {
                        
                        $(transferId).find(checkboxItem).eq(i).prop("checked", true);
                    }
                }
                $(addSelected).addClass("btn-arrow-active");
            } else {
                for (var i = 0; i < $(transferId).find(checkboxItem).length; i++) {
                    if ($(transferId).find(transferDoubleListLi).eq(i).css('display') != "none" && $(transferId).find(checkboxItem).eq(i).is(':checked')) {
                        $(transferId).find(checkboxItem).eq(i).prop("checked", false);
                    }
                }
                $(addSelected).removeClass("btn-arrow-active");
            }
        });

        $(groupsSelectAllId).on("click", function () {
            if ($(this).is(':checked')) {
                for (var i = 0; i < $(transferId).find(groupCheckboxItem).length; i++) {
                    if ($(transferId).find(transferDoubleGroupListLiUlLi).eq(i).css('display') != "none" && !$(transferId).find(groupCheckboxItem).eq(i).is(':checked')) {
                       
                        $(transferId).find(groupCheckboxItem).eq(i).prop("checked", true);
                    }
                    if (!$(transferId).find(groupSelectAll).eq(i).is(':checked')) {
                        $(transferId).find(groupSelectAll).eq(i).prop("checked", true);
                    }
                }
                $(addSelected).addClass("btn-arrow-active");
            } else {
                for (var i = 0; i < $(transferId).find(groupCheckboxItem).length; i++) {
                    if ($(transferId).find(transferDoubleGroupListLiUlLi).eq(i).css('display') != "none" && $(transferId).find(groupCheckboxItem).eq(i).is(':checked')) {
                        $(transferId).find(groupCheckboxItem).eq(i).prop("checked", false);
                    }
                    if ($(transferId).find(groupSelectAll).eq(i).is(':checked')) {
                        $(transferId).find(groupSelectAll).eq(i).prop("checked", false);
                    }
                }
                $(addSelected).removeClass("btn-arrow-active");
            }
        });

        
        $(addSelected).on("click", function () {
            var listHtmlStr = "";
            var selectedItemNum = 0;
           
            if ($(transferId).find(tabContentFirst).css("display") != "none") {
                for (var i = 0; i < $(transferId).find(groupCheckboxItem).length; i++) {
                    if ($(transferId).find(groupCheckboxItem).eq(i).is(':checked')) {
                        var checkboxItemId = $(transferId).find(groupCheckboxItem).eq(i).attr("id");
                        var checkboxItemArray = checkboxItemId.split("_");
                        var groupIdIndex = checkboxItemArray[1];
                        var idIndex = checkboxItemArray[3];
                        var val = $(transferId).find(groupCheckboxName).eq(i).text();
                        var value = $(transferId).find(groupCheckboxItem).eq(i).val();
                        $(transferId).find(transferDoubleGroupListLiUlLi).eq(i).css('display', 'none');
                        listHtmlStr = listHtmlStr + '<li class="transfer-double-selected-list-li transfer-double-selected-list-li-' + currentTimeStr + ' .clearfix">' +
                            '<div class="checkbox-group">' +
                            '<input type="checkbox" value="' + value + '" class="checkbox-normal checkbox-selected-item-' + currentTimeStr + '" id="group_' + groupIdIndex + '_selectedCheckbox_' + idIndex + '_' + currentTimeStr + '">' +
                            '<label class="checkbox-selected-name-' + currentTimeStr + '" for="group_' + groupIdIndex + '_selectedCheckbox_' + idIndex + '_' + currentTimeStr + '">' + val + '</label>' +
                            '</div>' +
                            '</li>'
                        selectedItemNum = selectedItemNum + 1;
                    }
                }
                for (var j = 0; j < $(transferId).find(groupSelectAll).length; j++) {
                    if ($(transferId).find(groupSelectAll).eq(j).is(":checked")) {
                        $(transferId).find(groupSelectAll).eq(j).prop("disabled", "disabled");
                    }
                }
                $(transferId).find(groupTotalNum).empty();
               
                new_group_total_num = total_group_num - selectedItemNum;
                
                selected_total_num = selectedItemNum;
                var new_total_num_str = new_group_total_num + " Items";
                
                $(transferId).find(groupTotalNum).append(new_total_num_str);
                
                $(transferId).find(selectedTotalNum).text(selected_total_num + " Items");
                if (new_group_total_num == 0) {
                    $(groupsSelectAllId).prop("checked", true);
                    $(groupsSelectAllId).prop("disabled", "disabled");
                }
            } else {
                
                for (var i = 0; i < $(transferId).find(checkboxItem).length; i++) {
                    if ($(transferId).find(checkboxItem).eq(i).is(':checked')) {
                        var checkboxItemId = $(transferId).find(checkboxItem).eq(i).attr("id");
                        var idIndex = checkboxItemId.split("_")[1];
                        var val = $(transferId).find(checkboxName).eq(i).text();
                        var value = $(transferId).find(checkboxItem).eq(i).val();
                        $(transferId).find(transferDoubleListLi).eq(i).css('display', 'none');
                        listHtmlStr = listHtmlStr + '<li class="transfer-double-selected-list-li  transfer-double-selected-list-li-' + currentTimeStr + ' .clearfix">' +
                            '<div class="checkbox-group">' +
                            '<input type="checkbox" value="' + value + '" class="checkbox-normal checkbox-selected-item-' + currentTimeStr + '" id="selectedCheckbox_' + idIndex + '_' + currentTimeStr + '">' +
                            '<label class="checkbox-selected-name-' + currentTimeStr + '" for="selectedCheckbox_' + idIndex + '_' + currentTimeStr + '">' + val + '</label>' +
                            '</div>' +
                            '</li>';
                        selectedItemNum = selectedItemNum + 1;
                    }
                }
                $(transferId).find(totalNum).empty();
               
                new_total_num = total_num - selectedItemNum;
                
                selected_total_num = selectedItemNum;
                var new_total_num_str = new_total_num + " Items";
                
                $(transferId).find(totalNum).append(new_total_num_str);
                
                $(transferId).find(selectedTotalNum).text(selected_total_num + " Items");
                if (new_total_num == 0) {
                    $(selectAllId).prop("checked", true);
                    $(selectAllId).prop("disabled", "disabled");
                }
            }
            $(addSelected).removeClass("btn-arrow-active");
            $(transferId).find(transferDoubleSelectedListUl).empty();
            $(transferId).find(transferDoubleSelectedListUl).append(listHtmlStr);
            
            callable.call(this, getSelected(), getSelectedName());
        });

       
        $(deleteSelected).on("click", function () {
            var deleteItemNum = 0;
            
            if ($(transferId).find(tabContentFirst).css("display") != "none") {
                for (var i = 0; i < $(transferId).find(checkboxSelectedItem).length;) {
                    if ($(transferId).find(checkboxSelectedItem).eq(i).is(':checked')) {
                        var checkboxSelectedItemId = $(transferId).find(checkboxSelectedItem).eq(i).attr("id");
                        var groupItemIdArray = checkboxSelectedItemId.split("_")
                        var groupId = groupItemIdArray[1];
                        var idIndex = groupItemIdArray[3];
                        $(transferId).find(transferDoubleSelectedListLi).eq(i).remove();
                        $(transferId).find("#group_" + groupId + "_" + currentTimeStr).prop("checked", false);
                        $(transferId).find("#group_" + groupId + "_" + currentTimeStr).removeAttr("disabled");
                        $(transferId).find("#group_" + groupId + "_checkbox_" + idIndex + "_" + currentTimeStr).prop("checked", false);
                        $(transferId).find("#group_" + groupId + "_checkbox_" + idIndex + "_" + currentTimeStr).parent().parent().css('display', 'block');
                        deleteItemNum = deleteItemNum + 1;
                    } else {
                        i++;
                    }
                }
                $(transferId).find(groupTotalNum).empty();
                
                new_group_total_num = new_group_total_num + deleteItemNum;
               
                selected_total_num -= deleteItemNum;
                var new_total_num_str = new_group_total_num + " Items";
                
                $(transferId).find(groupTotalNum).append(new_total_num_str);
                
                $(transferId).find(selectedTotalNum).text(selected_total_num + " Items");
                if ($(groupsSelectAllId).is(':checked')) {
                    $(groupsSelectAllId).prop("checked", false);
                    $(groupsSelectAllId).removeAttr("disabled");
                }
            } else {
                
                for (var i = 0; i < $(transferId).find(checkboxSelectedItem).length;) {
                    if ($(transferId).find(checkboxSelectedItem).eq(i).is(':checked')) {
                        var checkboxSelectedItemId = $(transferId).find(checkboxSelectedItem).eq(i).attr("id");
                        var idIndex = checkboxSelectedItemId.split("_")[1];
                        var val = $(transferId).find(checkboxSelectedName).eq(i).text();
                        $(transferId).find(transferDoubleSelectedListLi).eq(i).remove();
                        $(transferId).find(checkboxItem).eq(idIndex).prop("checked", false);
                        $(transferId).find(transferDoubleListLi).eq(idIndex).css('display', 'block');
                        deleteItemNum = deleteItemNum + 1;
                    } else {
                        i++;
                    }
                }
                $(transferId).find(totalNum).empty();
                
                new_total_num = new_total_num + deleteItemNum;
                
                selected_total_num -= deleteItemNum;
                var new_total_num_str = new_total_num + " Items";
               
                $(transferId).find(totalNum).append(new_total_num_str);
                
                $(transferId).find(selectedTotalNum).text(selected_total_num + " Items");
                if ($(selectAllId).is(':checked')) {
                    $(selectAllId).prop("checked", false);
                    $(selectAllId).removeAttr("disabled");
                }
            }
            $(deleteSelected).removeClass("btn-arrow-active");
            
            callable.call(this, getSelected(), getSelectedName());
        });

        
        $(listSearchId).on("keyup", function () {
            
            $(transferId).find(transferDoubleListUl).css('display', 'block');
            
            if ($(listSearchId).val() == "") {
                for (var i = 0; i < $(transferId).find(checkboxItem).length; i++) {
                    if (!$(transferId).find(checkboxItem).eq(i).is(':checked')) {
                        $(transferId).find(transferDoubleListLi).eq(i).css('display', 'block');
                    }
                }
                return;
            }

            
            $(transferId).find(transferDoubleListLi).css('display', 'none');

            for (var j = 0; j < $(transferId).find(transferDoubleListLi).length; j++) {
                
                if (!$(transferId).find(checkboxItem).eq(j).is(':checked')
                    && $(transferId).find(transferDoubleListLi).eq(j).text()
                        .substr(0, $(listSearchId).val().length).toLowerCase() == $(listSearchId).val().toLowerCase()) {
                    $(transferId).find(transferDoubleListLi).eq(j).css('display', 'block');
                }
            }
        });

       
        $(groupListSearchId).on("keyup", function () {
            
            $(transferId).find(transferDoubleGroupListUl).css('display', 'block');
            
            if ($(groupListSearchId).val() == "") {
                for (var i = 0; i < $(transferId).find(groupCheckboxItem).length; i++) {
                    if (!$(transferId).find(checkboxItem).eq(i).is(':checked')) {
                        
                        $(transferId).find(transferDoubleGroupListLiUlLi).eq(i).parent().parent().css('display', 'block');
                        
                        $(transferId).find(transferDoubleGroupListLiUlLi).eq(i).css('display', 'block');
                    }
                }
                return;
            }

            
            $(transferId).find(transferDoubleGroupListLi).css('display', 'none');
            $(transferId).find(transferDoubleGroupListLiUlLi).css('display', 'none');

            for (var j = 0; j < $(transferId).find(transferDoubleGroupListLiUlLi).length; j++) {
                
                if (!$(transferId).find(groupCheckboxItem).eq(j).is(':checked')
                    && $(transferId).find(transferDoubleGroupListLiUlLi).eq(j).text()
                        .substr(0, $(groupListSearchId).val().length).toLowerCase() == $(groupListSearchId).val().toLowerCase()) {
                    
                    $(transferId).find(transferDoubleGroupListLiUlLi).eq(j).parent().parent().css('display', 'block');
                    $(transferId).find(transferDoubleGroupListLiUlLi).eq(j).css('display', 'block');
                }
            }
        });

       
        $(selectedListSearchId).keyup(function () {
            
            $(transferId).find(transferDoubleSelectedListUl).css('display', 'block');

            
            if ($(selectedListSearchId).val() == "") {
                $(transferId).find(transferDoubleSelectedListLi).css('display', 'block');
                return;
            }
            $(transferId).find(transferDoubleSelectedListLi).css('display', 'none');

            for (var i = 0; i < $(transferId).find(transferDoubleSelectedListLi).length; i++) {
                
                if ($(transferId).find(transferDoubleSelectedListLi).eq(i).text()
                        .substr(0, $(selectedListSearchId).val().length).toLowerCase() == $(selectedListSearchId).val().toLowerCase()) {
                    $(transferId).find(transferDoubleSelectedListLi).eq(i).css('display', 'block');
                }
            }
        });
    }

    
    function generateLeftList(currentTimeStr, data, itemName, valueName) {
        var listHtmlStr = "";
        for (var i = 0; i < data.length; i++) {
            listHtmlStr = listHtmlStr +
                '<li class="transfer-double-list-li transfer-double-list-li-' + currentTimeStr + '">' +
                '<div class="checkbox-group">' +
                '<input type="checkbox" value="' + data[i][valueName] + '" class="checkbox-normal checkbox-item-' + currentTimeStr + '" id="itemCheckbox_' + i + '_' + currentTimeStr + '">' +
                '<label class="checkbox-name-' + currentTimeStr + '" for="itemCheckbox_' + i + '_' + currentTimeStr + '">' + data[i][itemName] + '</label>' +
                '</div>' +
                '</li>'
        }
        return listHtmlStr;
    }

    
    function generateLeftGroupList(currentTimeStr, data, itemName, groupListName, groupItemName, valueName) {
        var listHtmlStr = "";
        for (var i = 0; i < data.length; i++) {
            listHtmlStr = listHtmlStr +
                '<li class="transfer-double-group-list-li transfer-double-group-list-li-' + currentTimeStr + '">'
                + '<div class="checkbox-group">' +
                '<input type="checkbox" class="checkbox-normal group-select-all-' + currentTimeStr + '" id="group_' + i + '_' + currentTimeStr + '">' +
                '<label for="group_' + i + '_' + currentTimeStr + '" class="group-name-' + currentTimeStr + '">' + data[i][groupItemName] + '</label>' +
                '</div>';
            if (data[i][groupListName].length > 0) {
                listHtmlStr = listHtmlStr + '<ul class="transfer-double-group-list-li-ul transfer-double-group-list-li-ul-' + currentTimeStr + '">'
                for (var j = 0; j < data[i][groupListName].length; j++) {
                    listHtmlStr = listHtmlStr + '<li class="transfer-double-group-list-li-ul-li transfer-double-group-list-li-ul-li-' + currentTimeStr + '">' +
                        '<div class="checkbox-group">' +
                        '<input type="checkbox" value="' + data[i][groupListName][j][valueName] + '" class="checkbox-normal group-checkbox-item-' + currentTimeStr + ' belongs-group-' + i + '-' + currentTimeStr + '" id="group_' + i + '_checkbox_' + j + '_' + currentTimeStr + '">' +
                        '<label for="group_' + i + '_checkbox_' + j + '_' + currentTimeStr + '" class="group-checkbox-name-' + currentTimeStr + '">' + data[i][groupListName][j][itemName] + '</label>' +
                        '</div>' +
                        '</li>';
                }
                listHtmlStr = listHtmlStr + '</ul>'
            } else {
                listHtmlStr = listHtmlStr + '</li>';
            }
            listHtmlStr = listHtmlStr + '</li>';
        }
        return listHtmlStr;
    }

    
    function getGroupNum(data, groupListName) {
        var total_group_num = 0;
        for (var i = 0; i < data.length; i++) {
            var groupData = data[i][groupListName];
            if (groupData.length > 0) {
                total_group_num = total_group_num + groupData.length;
            }
        }
        return total_group_num;
    }


   
    function getSelected() {
        
        var transferId = "#transfer_double_" + inputId;
        var selected = [];
        var transferDoubleSelectedListLi = ".transfer-double-selected-list-li-" + currentTimeStr;

        for (var i = 0; i < $(transferId).find(transferDoubleSelectedListLi).length; i++) {
            
            var value = $(transferId).find(transferDoubleSelectedListLi).eq(i).find(".checkbox-group").find("input").val();
            selected.push(value);
        }
        return selected;
    }

    
    function getSelectedName() {
        
        var transferId = "#transfer_double_" + inputId;
        var selected = [];
        var transferDoubleSelectedListLi = ".transfer-double-selected-list-li-" + currentTimeStr;

        for (var i = 0; i < $(transferId).find(transferDoubleSelectedListLi).length; i++) {
            
            var value = $(transferId).find(transferDoubleSelectedListLi).eq(i).find(".checkbox-group").find("label").text();
            selected.push(value);
        }
        return selected;
    }

   
    function generateTransfer(inputId, currentTimeStr) {
        var htmlStr =
            '<div class="transfer-double" id="transfer_double_' + inputId + '">'
            + '<div class="transfer-double-header"></div>'
            + '<div class="transfer-double-content clearfix">'
            + '<div class="transfer-double-content-left">'
            + '<div class="transfer-double-content-tabs">'
          
            + '<div class="tab-item-name tab-item-name-' + currentTimeStr + '">Items</div>'
            + '</div>'

            + '<div class="transfer-double-list transfer-double-list-' + currentTimeStr + ' tab-content-first-' + currentTimeStr + ' tab-content-active">'
            + '<div class="transfer-double-list-header">'
            + '<div class="transfer-double-list-search">'
            + '<input class="transfer-double-list-search-input" type="text" id="groupListSearch_' + currentTimeStr + '" placeholder="Search" value="" />'
            + '</div>'
            + '</div>'
            + '<div class="transfer-double-list-content">'
            + '<div class="transfer-double-list-main">'
            + '<ul class="transfer-double-group-list-ul transfer-double-group-list-ul-' + currentTimeStr + '">'
            + '</ul>'
            + '</div>'
            + '</div>'
            + '<div class="transfer-double-list-footer">'
            + '<div class="checkbox-group">'
            + '<input type="checkbox" class="checkbox-normal" id="groupsSelectAll_' + currentTimeStr + '"><label for="groupsSelectAll_' + currentTimeStr + '" class="group_total_num_' + currentTimeStr + '"></label>'
            + '</div>'
            + '</div>'
            + '</div>'

            + '<div class="transfer-double-list transfer-double-list-' + currentTimeStr + '">'
            + '<div class="transfer-double-list-header">'
            + '<div class="transfer-double-list-search">'
            + '<input class="transfer-double-list-search-input" type="text" id="listSearch_' + currentTimeStr + '" placeholder="搜索" value="" />'
            + '</div>'
            + '</div>'
            + '<div class="transfer-double-list-content">'
            + '<div class="transfer-double-list-main">'
            + '<ul class="transfer-double-list-ul transfer-double-list-ul-' + currentTimeStr + '">'
            + '</ul>'
            + '</div>'
            + '</div>'
            + '<div class="transfer-double-list-footer">'
            + '<div class="checkbox-group">'
            + '<input type="checkbox" class="checkbox-normal" id="selectAll_' + currentTimeStr + '"><label for="selectAll_' + currentTimeStr + '" class="total_num_' + currentTimeStr + '"></label>'
            + '</div>'
            + '</div>'
            + '</div>'
            + '</div>'

            + '<div class="transfer-double-content-middle">'
            + '<div class="btn-select-arrow" id="add_selected_' + currentTimeStr + '"><i class="iconfont icon-forward"></i></div>'
            + '<div class="btn-select-arrow" id="delete_selected_' + currentTimeStr + '"><i class="iconfont icon-back"></i></div>'
            + '</div>'
            + '<div class="transfer-double-content-right">'
            + '<div class="transfer-double-content-param">'
            + '<div class="param-item">Selected</div>'
            + '</div>'
            + '<div class="transfer-double-selected-list">'
            + '<div class="transfer-double-selected-list-header">'
            + '<div class="transfer-double-selected-list-search">'
            + '<input class="transfer-double-selected-list-search-input" type="text" id="selectedListSearch_' + currentTimeStr + '" placeholder="Search" value="" />'
            + '</div>'
            + '</div>'
            + '<div class="transfer-double-selected-list-content">'
            + '<div class="transfer-double-selected-list-main">'
            + '<ul class="transfer-double-selected-list-ul transfer-double-selected-list-ul-' + currentTimeStr + '">'
            + '</ul>'
            + '</div>'
            + '</div>'
            + '<div class="transfer-double-list-footer">'
            + '<label class="selected_total_num_' + currentTimeStr + '">0 Item</label>'
            + '</div>'
            + '</div>'
            + '</div>'
            + '</div>'
            + '<div class="transfer-double-footer">'
            + '</div>'
            + '</div>';
        return htmlStr;
    }

    return {
        transfer: transfer
    }
})($);
