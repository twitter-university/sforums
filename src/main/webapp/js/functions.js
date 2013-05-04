function executeDelete(href, donefn, failfn) {
	if (confirm('Are you sure you wish to delete this item?')) {
		$.ajax({
			url : href,
			type : "DELETE"
		}).done(donefn).fail(failfn);
	}
}

function removeElement(element) {
	element.fadeOut(500, function() {
		$(this).remove();
	});
}

function executeDeleteAndRemoveContainer(deleteSelector, containerSelector) {
	$(deleteSelector).click(function(event) {
		event.preventDefault();
		var container = $(this).closest(containerSelector);
		executeDelete($(this).attr("href"), function() {
			removeElement(container);
		});
		return false;
	});
}

function executeDeleteAndRedirect(deleteSelector, redirectUrl) {
	$(deleteSelector).click(function(event) {
		event.preventDefault();
		executeDelete($(this).attr("href"), function() {
			window.location.href = redirectUrl;
		});
		return false;
	});
}