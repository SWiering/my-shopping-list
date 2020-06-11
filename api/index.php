<?php

function createItem($name){
    return array(
        'name' => $name,
        'image' => "$name.png"
    );
}

$entries = array(
    'items' => array(
        createItem('Clothing'),
        createItem('Lamp'),
        createItem('Ice'),
        createItem('Flowers'),
        createItem('Coffee'),
        createItem('Snacks'),
        createItem('Lettuce')
    )
);

header('Content-Type: application/json');
echo json_encode($entries);
?>