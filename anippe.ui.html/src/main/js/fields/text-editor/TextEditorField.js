import { BasicField, fields, texts, objects, strings, ObjectFactory } from '@eclipse-scout/core';

import tinymce from './tinymce/tinymce.min.js';

export default class TextEditorField extends BasicField {

	constructor() {
		super();

		this.editor = null;
	}

	_init(model) {
		super._init(model);
	}

	_initValue(value) {
		value = texts.resolveText(value, this.session.locale.languageTag);
		super._initValue(value);
	}

	_renderDisplayText() {
		tinymce.activeEditor.setContent(this.displayText);
		super._renderDisplayText();
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
			tinymce.activeEditor.setMode(this.enabledComputed ? 'design' : 'readonly')
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
		this.editor = tinymce.init({
			selector: '.scout-editor',
			forced_root_blocks: false,
			forced_root_block: '',
			force_br_newlines: true,
			height: '100%',
			newline_behavior: 'block',
			plugins: [
				'advlist autolink lists link image charmap print preview anchor',
				'searchreplace visualblocks fullscreen',
				'paste'
			],
			//skin: (window.matchMedia("(prefers-color-scheme: dark)").matches ? "oxide-dark" : ""),
            //content_css: (window.matchMedia("(prefers-color-scheme: dark)").matches ? "dark" : ""),
			preformatted: false,
			branding: false,
			toolbar: 'undo redo | formatselect | fontsizeselect | ' +
				'bold italic forecolor backcolor | alignleft aligncenter ' +
				'alignright alignjustify | bullist numlist',
			menu: {
				edit: { title: 'Edit', items: 'undo redo | cut copy paste pastetext | selectall | searchreplace' },
				view: { title: 'View', items: 'code | visualaid visualchars visualblocks | spellchecker | preview fullscreen' },
				insert: { title: 'Insert', items: 'image link | charmap emoticons hr | pagebreak nonbreaking anchor  | insertdatetime' },
				format: { title: 'Format', items: 'bold italic underline strikethrough superscript subscript | styles blocks fontfamily fontsize align lineheight | forecolor backcolor | language | removeformat' },
				tools: { title: 'Tools', items: 'spellchecker spellcheckerlanguage | a11ycheck code wordcount' }
			},
			//menubar:false,
			statusbar: false,
			fontsize_formats: "8pt 9pt 10pt 11pt 12pt 14pt 18pt 24pt 30pt 36pt 48pt 60pt 72pt 96pt",
			setup: function(editor) {
				editor.on('init', function(e) {
					that.editor = e.target;
				});

				editor.on('input', function(e) {
					let content = editor.getContent();
					if (that.$field != null) {
						that.$field.val(content);

						that.acceptInput(true)
					}
				});

				editor.on('NodeChange', function(e) {
					let content = editor.getContent();
					if (that.$field != null) {
						that.$field.val(content);

						that.acceptInput(true)
					}
				});

				editor.on('SetContent', function(e) {
					let content = editor.getContent();
					if (that.$field != null) {
						that.$field.val(content);

						that.acceptInput(true)
					}
				});
			},
			content_style: 'p {margin: 5px; padding: 0;} body { font-family:DM Sans, helvetica, sans-serif; font-size:10pt; line-height: 1.3; }' //
		});
	}

}
