<?php require TPL_INC . 'header.php'; ?>

<?php if (isset($error_msg)): ?>
    <div class="error_message"><?= html_escape($error_msg); ?></div>
<?php endif; ?>


<h1>Workout</h1>

<form action="" method="POST">

    <select name="Select Workout">
        <!-- Test Values -->
        <option selected disabled>Choose Workout</option>
        <option>Arm Workout</option>
        <option>Abs Workout</option>
        <option>Legs Workout</option>
    </select>

    <fieldset>
        <legend>Exercise1</legend>
        <input type="number" name="reps1" required placeholder="Repetitions">
    </fieldset>
    <fieldset>
        <legend>Exercise2</legend>
        <input type="number" name="reps2" required placeholder="Repetitions">
    </fieldset>
    <fieldset> 
        <legend>Exercise3</legend>
        <input type="number" name="reps3" required placeholder="Repetitions">
    </fieldset>
    <input type="submit" value="Submit workout">
</form>


<?php require TPL_INC . 'footer.php'; ?>


