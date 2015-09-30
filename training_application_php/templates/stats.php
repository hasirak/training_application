<?php require TPL_INC . 'header.php'; ?>

<h1>Stats</h1>



<h2>Your last completed workouts:</h2>
<ul>
<?php if($workout_log):?>
    <?php foreach($workout_log as $w): ?>
        <li><a href="/workout/<?=html_escape($w->workout_id);?>"><?=html_escape($w->workout_name); ?> - <?=$w->workout_date;?></a></li>
    <?php endforeach; ?>

<?php else: ?>
    <li>You have not completed any workouts</li>
<?php endif; ?>
</ul>

<?php require TPL_INC . 'footer.php'; ?>

