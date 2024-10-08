document.addEventListener("DOMContentLoaded", function() {
    console.log("안녕");
    const blockEls = document.querySelectorAll('form[id^="block-"]');

    blockEls.forEach(function(blockEl) {
        const selectEl = blockEl.querySelector('#select');

        const userId = blockEl.id.split('-')[1];

        if(selectEl.innerText == '0') {
            selectEl.innerText = '차단';
            blockEl.action = `/admin/members/block/${userId}`;
        } else{
            selectEl.innerText = '차단해제';
            blockEl.action = `/admin/members/unblock/${userId}`;
        }
    });
});