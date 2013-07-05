function extract_short_name(object_array) {
    var cat_names = [];
    for (var i = 0; i < object_array.length; i++) {
        cat_names.push(object_array[i].short_name);
    }
    return cat_names;
}