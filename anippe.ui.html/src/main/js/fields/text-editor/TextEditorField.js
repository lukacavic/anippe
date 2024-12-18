import { BasicField, fields, texts } from '@eclipse-scout/core';
import tinymce from './tinymce.min.js';

export default class TextEditorField extends BasicField {

	constructor() {
		super();

		this.editor = null;
		this.editorSelection = null;
	}

	_init(model) {
		super._init(model);
	}

	_initValue(value) {
		value = texts.resolveText(value, this.session.locale.languageTag);
		super._initValue(value);
	}

	_renderDisplayText() {
		super._renderDisplayText();
		tinymce.activeEditor.setContent(this.displayText);
	}

	_readDisplayText() {
		return this.$field ? this.$field.val() : '';
	}

	_destroy() {
		try {
			tinymce.remove(".scout-editor");
		} catch (e) { }
	}

	_renderEnabled() {
		super._renderEnabled();
		if (this.editor) {
			tinymce.activeEditor.mode.set(this.enabledComputed ? 'design' : 'readonly')
		}
	}

	_render() {
		this.addContainer(this.$parent, 'text-editor-field');
		this.addLabel();
		this.addStatus();
		this.addMandatoryIndicator();

		var $scoutField = fields.makeTextField(this.$container, 'scout-editor');
		this.addField($scoutField);

		let that = this;

		let customFontSize = 10;

		if (customFontSize == null || customFontSize == '') {
			customFontSize = '10';
		}

		tinymce.suffix = '.min'; // [Mario] ovo lokalno kod mene da bi editor delal -> NE KOMITATI

		this.editor = tinymce.init({
			convert_newlines_to_brs : true,
			forced_root_block: false,
			selector: '.scout-editor',
			forced_root_blocks: false,
			force_br_newlines: true,
			newline_behavior: 'invert',
			language: 'hr',
			promotion: false,
			height: '100%',
			license_key: 'gpl',
			base_url: '/tinymce',
			width: '100%',
			preformatted: true,
			branding: false,
			statusbar: false,
			font_size_formats: "8pt 9pt 10pt 11pt 12pt 14pt 18pt 24pt 30pt 36pt 48pt 60pt 72pt 96pt",
			toolbar: 'fontfamily  | fullscreen | undo redo | blocks  | fontsize | ' +
				'bold italic forecolor backcolor | alignleft aligncenter ' +
				'alignright alignjustify | bullist numlist',
			menu: {
				edit: { title: 'Edit', items: 'undo redo | cut copy paste pastetext | selectall | searchreplace' },
				view: { title: 'View', items: 'code | visualaid visualchars visualblocks | spellchecker | preview fullscreen' },
				insert: { title: 'Insert', items: 'image link media addcomment pageembed template codesample inserttable | charmap emoticons hr | pagebreak nonbreaking anchor tableofcontents | insertdatetime' },
				format: { title: 'Format', items: 'bold italic underline strikethrough superscript subscript codeformat | styles blocks fontfamily fontsize align lineheight | forecolor backcolor | language | removeformat' },
				tools: { title: 'Tools', items: 'spellchecker spellcheckerlanguage | a11ycheck code wordcount' }
			},
			plugins: "fullscreen,advlist,autolink,lists,link,image,charmap,preview,anchor,table",
			setup: function(editor) {
				editor.on('init', function(e) {
					that.editor = e.target;
					that.editor.setContent(that.displayText);
				});


				/*editor.on('input', function(e) {
					let content = editor.getContent();
					console.log("input event, sadrzaj je:", content);
					if (that.$field != null  && that.$field !== "") {
						that.$field.val(content);
						console.log("input event, usao u accept")
						that.acceptInput(true)
					}
				});*/

				editor.on('NodeChange input', function(e) {//dodan input event, za bug helena: nalaz se naknadno nekad ne bi spremao.
					let content = editor.getContent();
					if (that.$field != null) {
						that.$field.val(content);
						that.acceptInput(true)
					}
				});

				/*
				Kad se otvori editor, samo promjeni font, bez promjene sadržaja, onda ide FormtApply event. Inače ne sprema sadržaj.
				*/
				editor.on('FormatRemove', function(e) {
					let content = editor.getContent();
					if (that.$field != null) {
						that.$field.val(content);

						that.acceptInput(true)
					}
				});

				editor.on('FormatApply', function(e) {
					let content = editor.getContent();
					if (that.$field != null) {
						that.$field.val(content);

						that.acceptInput(true)
					}
				});

				/*editor.on('NodeChange', function(e) {
					console.log("usao u NodeChange..",editor.getContent())
					let content = editor.getContent();
					if (that.$field != null) {
						that.$field.val(content);

						that.acceptInput(true)
					}
				});

				editor.on('SetContent', function(e) {
					console.log("usao u SetContent..")
					let content = editor.getContent();
					if (that.$field != null) {
						that.$field.val(content);

						that.acceptInput(true)
					}
				});*/
			},
			//content_style: 'p {margin: 5px; padding: 0;} body { font-family:arial, helvetica, sans-serif; font-size:10pt; line-height: 1.3; }' //
			content_style: 'p {margin: 5px; padding: 0;} body { font-family:arial, helvetica, sans-serif; font-size:' + customFontSize + 'pt; line-height: 1.3; }' //
		});
	}

	// Function to insert content at the cursor position between paragraphs
	insertContentBetween(content) {
		const editor = this.editor;
		this.editorSelection = editor.selection;

		// Check if the editor has focus and a cursor position
		if (editor && editor.selection) {
			// Insert content at the cursor's location
			editor.selection.setContent(content);

			// Set new editor value to java field
			let content2 = editor.getContent();
			if (this.$field != null) {
				this.$field.val(content2);
				this.acceptInput(true);
			}
		} else {
			console.error("Cursor position is not available or editor is not focused.");
		}
	}
}
